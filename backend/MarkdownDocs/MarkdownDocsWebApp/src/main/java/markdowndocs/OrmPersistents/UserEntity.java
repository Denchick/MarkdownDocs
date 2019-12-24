package markdowndocs.OrmPersistents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class UserEntity {

    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String passwordHash;
    @Column(name = "authToken")
    private String authToken;
    @Column(name = "expireAt")
    private Timestamp expireAt;

    public UserEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }



    public static UserEntity CreateEntityWithPasswordHashing(String login, String password, UUID userId) {
        UserEntity newUser = new UserEntity();
        newUser.setLogin(login);
        //encrypt password
        newUser.setPasswordHash(password);
        newUser.setId(userId);
        return newUser;
    }

    public Timestamp getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Timestamp expireAt) {
        this.expireAt = expireAt;
    }
}
