package markdowndocs.documentstorage;

public class Document {
    private MetaInfo metaInfo;
    private String content;

    public Document() {}

    public Document(MetaInfo metaInfo, String content) {
        this.metaInfo = metaInfo;
        this.content = content;
    }

    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    public String getContent() {
        return content;
    }

}
