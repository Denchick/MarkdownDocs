package markdowndocs.OrmPersistents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
public class DocumentEntity {

    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "title")
    private String title;
    @Column(name = "owner")
    private UUID owner;
    @Column(name = "createAt")
    private Timestamp createAt;
    @Column(name = "editedAt")
    private Timestamp editedAt;
    @Column(name = "content", columnDefinition = "Text")
    private String content;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DocumentEntity))
            return false;

        DocumentEntity asEntity = (DocumentEntity)obj;

        return asEntity.getId() == this.getId()
                && asEntity.getContent().equals(getContent())
                && asEntity.getTitle().equals(getTitle())
                && asEntity.getCreateAt().equals(getCreateAt())
                && asEntity.getEditedAt().equals(getEditedAt())
                && asEntity.getOwner().equals(getOwner());

    }

    public DocumentEntity() {
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(Timestamp editedAt) {
        this.editedAt = editedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
