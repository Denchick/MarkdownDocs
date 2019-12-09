package markdowndocs.documentstorage;

import java.util.UUID;

public class MetaInfo {
    private UUID id;
    private String title;
    private String author;

    public MetaInfo() {}

    public MetaInfo(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public UUID getId() { return id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // just for mocking
    public void setId(UUID id) {
        this.id = id;
    }
}
