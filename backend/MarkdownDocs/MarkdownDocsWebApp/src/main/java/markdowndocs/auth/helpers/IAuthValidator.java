package markdowndocs.auth.helpers;

import markdowndocs.auth.AuthCredentials;

public interface IAuthValidator {
    public boolean IsValidateCredentials(AuthCredentials credentials);
}

