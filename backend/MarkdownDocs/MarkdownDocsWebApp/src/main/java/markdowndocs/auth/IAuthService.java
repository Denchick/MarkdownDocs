package markdowndocs.auth;


import markdowndocs.infrastructure.Result;
import markdowndocs.infrastructure.ValueResult;
import org.javatuples.Pair;

import java.util.UUID;

public interface IAuthService {

    public Result<AuthError> Registry(AuthCredentials credentials);

    public ValueResult<Pair<UUID, String>, AuthError> Login(AuthCredentials credentials);

    public boolean Authorized(String authToken, UUID userId);

    public boolean HaveAccess(UUID userId, UUID documentId);
}

