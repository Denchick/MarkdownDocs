package markdowndocs.documents.sharing;

import markdowndocs.documentstorage.Document;
import markdowndocs.infrastructure.ValueResult;

import java.util.UUID;

public interface ISharingService {

    public ValueResult<String, ShareError> ShareDocument(UUID documentId);

    public ValueResult<UUID, ShareError> GetDocumentIdByToken(String token);
}
