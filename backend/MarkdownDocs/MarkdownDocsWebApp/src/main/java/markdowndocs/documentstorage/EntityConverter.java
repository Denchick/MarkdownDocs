package markdowndocs.documentstorage;

import markdowndocs.OrmPersistents.DocumentEntity;

import java.sql.Timestamp;
import java.util.UUID;

public class EntityConverter {

    public static DocumentEntity DocumentToDbEntity(Document document, UUID owner) {
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setContent(document.getContent());
        documentEntity.setOwner(owner);

        MetaInfo metaInfo = document.getMetaInfo();

        documentEntity.setId(document.getMetaInfo().getId());

        documentEntity.setTitle(metaInfo.getTitle());
        Timestamp createAt = new Timestamp(metaInfo.getCreateAt().getTime());
        Timestamp editedAt = new Timestamp(metaInfo.getEditedAt().getTime());
        documentEntity.setCreateAt(createAt);
        documentEntity.setEditedAt(editedAt);

        return documentEntity;
    }

    public static Document DbEntityToDocument(DocumentEntity documentEntity) {
        MetaInfo metaInfo = new MetaInfo();
        metaInfo.setId(documentEntity.getId());
        metaInfo.setTitle(documentEntity.getTitle());
        metaInfo.setCreateAt(documentEntity.getCreateAt());
        metaInfo.setEditedAt(documentEntity.getEditedAt());

        return new Document(metaInfo, documentEntity.getContent());
    }
}
