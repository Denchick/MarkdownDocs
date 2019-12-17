package markdowndocs.documentstorage;

import java.sql.Timestamp;
import java.util.UUID;

public class Document {
    private MetaInfo metaInfo;
    private String content;

    public Document() {
    }

    public Document(MetaInfo metaInfo, String content) {
        this.metaInfo = metaInfo;
        this.content = content;
    }

    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Document))
            return false;
        Document objAsDocument = (Document) obj;

        return objAsDocument.metaInfo.equals(getMetaInfo()) && objAsDocument.content.equals(getContent());

    }

    public String getContent() {
        return content;
    }

    public static Document CreateBy(String title, String content) {
        MetaInfo newMetaInfo = new MetaInfo();

        Timestamp createAt = new Timestamp(System.currentTimeMillis());
        newMetaInfo.setId(UUID.randomUUID());
        newMetaInfo.setCreateAt(createAt);
        newMetaInfo.setEditedAt(createAt);
        newMetaInfo.setTitle(title);

        return new Document(newMetaInfo, content);
    }

}
