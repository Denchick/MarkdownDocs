package markdowndocs.auth;

public class CredentialsValidator implements IAuthValidator {

    @Override
    public boolean IsValidateCredentials(AuthCredentials credentials) {
        return true;
    }
}
