package markdowndocs.auth;


import markdowndocs.documentstorage.IQueryExecutor;
import markdowndocs.infrastructure.Result;
import markdowndocs.infrastructure.ValueResult;
import org.javatuples.Pair;

import java.util.UUID;

public interface IAuthService {

    public Result<AuthError> Registry(AuthCredentials credentials);

    public ValueResult<Pair<UUID, String>, AuthError> Login(AuthCredentials);

    public ValueResult<Boolean, AuthError> NotAuthorized(String authToken, UUID userId);

    public ValueResult<Boolean, AuthError> NotHaveAccess(UUID userId, UUID documentId);
}

