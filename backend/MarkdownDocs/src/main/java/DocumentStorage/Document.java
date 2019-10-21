package DocumentStorage;

public class Document {
    private String title;
    private String content;

    public Document(String title, String content)
    {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
