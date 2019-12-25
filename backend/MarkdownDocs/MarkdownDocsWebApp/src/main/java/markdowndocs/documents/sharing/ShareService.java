package markdowndocs.documents.sharing;

import markdowndocs.OrmPersistents.ShareEntity;
import markdowndocs.infrastructure.ResultsFactory;
import markdowndocs.infrastructure.ValueResult;
import markdowndocs.orm.IDataBaseAdapter;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShareService implements ISharingService {

    private IDataBaseAdapter dataBaseAdapter;
    private Logger logger;

    public ShareService(IDataBaseAdapter dataBaseAdapter, Logger logger) {
        this.dataBaseAdapter = dataBaseAdapter;
        this.logger = logger;
    }

    @Override
    public ValueResult<String, ShareError> ShareDocument(UUID documentId) {
        try {

            List<ShareEntity> storedLinks = dataBaseAdapter.GetEntityWhereEq("documentId", documentId, ShareEntity.class);

            if (storedLinks.size() > 1) {
                logger.log(Level.SEVERE, "Unexpected link state find several share link for document " + documentId.toString());
                return ResultsFactory.Failed(ShareError.UnknownError);
            }

            if (storedLinks.size() == 1) {
                ShareEntity storedLink = storedLinks.get(0);
                return ResultsFactory.Success(storedLink.getToken());
            }

            String linkForSharing = GenerateShareLink(documentId);
            ShareEntity shareEntity = new ShareEntity();
            shareEntity.setDocumentId(documentId);
            shareEntity.setToken(linkForSharing);
            shareEntity.setExpireAt(new Timestamp(System.currentTimeMillis()));
            dataBaseAdapter.Create(shareEntity);
            return ResultsFactory.Success(shareEntity.getToken());

        } catch (Exception error) {
            logger.log(Level.SEVERE, "Can not create share link. " + error.getMessage());
            return ResultsFactory.Failed(ShareError.UnknownError);
        }

    }

    @Override
    public ValueResult<UUID, ShareError> GetDocumentIdByToken(String token) {
        try {
            ShareEntity storedEntity = dataBaseAdapter.GetEntityBy(token, ShareEntity.class);
            if (storedEntity == null)
                return ResultsFactory.Failed(ShareError.NotFound);

            if (storedEntity.getExpireAt().before(new Timestamp(System.currentTimeMillis()))) {
                dataBaseAdapter.DeleteById(token, storedEntity.getClass());
                return ResultsFactory.Failed(ShareError.NotFound);
            }

            return ResultsFactory.Success(storedEntity.getDocumentId());

        } catch (Exception error) {
            logger.log(Level.SEVERE, "Can not get document by share link. " + error.getMessage());
            return ResultsFactory.Failed(ShareError.UnknownError);
        }
    }

    private String GenerateShareLink(UUID documentId) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(documentId.toString().getBytes());
        md.update(Long.toString(System.currentTimeMillis()).getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter
                .printHexBinary(digest).toLowerCase();
    }
}

