package markdowndocs.documents.sharing;

import markdowndocs.OrmPersistents.ShareEntity;
import markdowndocs.documentstorage.Document;
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

public interface ISharingService {

    public ValueResult<String, ShareError> ShareDocument(UUID documentId);

    public ValueResult<UUID, ShareError> GetDocumentIdByToken(String token);

    public ValueResult<String, ShareError> GetShareTokenById(UUID documentId);
}

