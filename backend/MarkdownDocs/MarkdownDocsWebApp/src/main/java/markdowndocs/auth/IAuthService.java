package markdowndocs.auth;

public interface IAuthService {
    public boolean HaveAccess(String user, String file);
}
