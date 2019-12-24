package markdowndocs.auth;

import markdowndocs.OrmPersistents.UserEntity;

import java.util.UUID;

public class AuthServiceHelper
{
    public static UUID CreateId(AuthCredentials credentials)
    {
        return UUID.randomUUID();
    }
    public static String GenerateAuth(AuthCredentials credentials)
    {
        return "";
    }

}
