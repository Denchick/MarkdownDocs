package markdowndocs.web.app.MarkdownDocsWebApp;

public class LoginResponseBody {

    public LoginResponseBody() {

    }

    public LoginResponseBody(String userId, String auth) {
        this.userId = userId;
        this.Auth = auth;
    }

    private String userId;
    private String Auth;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuth() {
        return Auth;
    }

    public void setAuth(String auth) {
        Auth = auth;
    }
}
