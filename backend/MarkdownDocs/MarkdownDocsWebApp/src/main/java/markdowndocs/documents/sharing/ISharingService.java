package markdowndocs.documents.sharing;

import markdowndocs.documentstorage.Document;

import java.util.UUID;

public interface ISharingService {

    public String ShareDocument(UUID documentId);

    public Document GetDocumentByToken(String token);
}
