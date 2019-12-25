package markdowndocs.auth.helpers;

import markdowndocs.auth.AuthCredentials;

public class CredentialsValidator implements IAuthValidator {

    @Override
    public boolean IsValidateCredentials(AuthCredentials credentials) {
        return true;
    }
}
