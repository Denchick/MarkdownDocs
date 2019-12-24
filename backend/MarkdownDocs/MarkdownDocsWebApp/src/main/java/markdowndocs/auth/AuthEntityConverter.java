package markdowndocs.auth;

import markdowndocs.OrmPersistents.UserEntity;

public class AuthEntityConverter
{
    public static UserEntity CreateFrom(AuthCredentials credentials)
    {
        return new UserEntity();
    }

}
