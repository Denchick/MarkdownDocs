package markdowndocs.auth;

public interface IAuthValidator
{
    public boolean IsValidateCredentials(AuthCredentials credentials);
}
