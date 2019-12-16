package markdowndocs.documentstorage;


import java.util.Date;
import java.util.UUID;

public class MetaInfo {
    private UUID id;
    private String title;
    private Date createAt;
    private Date editedAt;

    public MetaInfo() {
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MetaInfo))
            return false;

        MetaInfo asEntity = (MetaInfo) obj;

        return asEntity.getId().equals(this.getId())
                && asEntity.getTitle().equals(getTitle())
                && asEntity.getCreateAt().equals(getCreateAt())
                && asEntity.getEditedAt().equals(getEditedAt());
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(Date editedAt) {
        this.editedAt = editedAt;
    }

    // just for mocking
    public void setId(UUID id) {
        this.id = id;
    }
}
