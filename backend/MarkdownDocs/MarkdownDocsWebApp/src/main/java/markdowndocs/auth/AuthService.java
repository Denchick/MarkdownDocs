package markdowndocs.auth;

import markdowndocs.OrmPersistents.UserEntity;
import markdowndocs.documentstorage.IQueryExecutor;
import markdowndocs.infrastructure.Result;
import markdowndocs.infrastructure.ResultsFactory;
import markdowndocs.infrastructure.ValueResult;
import net.bytebuddy.implementation.Implementation;
import org.javatuples.Pair;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface IAuthValidator
{
    public boolean ValidateCredentials();
}


public class AuthService implements IAuthService {
    private IQueryExecutor queryExecutor;
    private Logger logger;

    @Override
    public Result<AuthError> Registry(AuthCredentials credentials) {
        UserEntity userEntity = AuthEntityConverter.CreateFrom(credentials);
        try {
            queryExecutor.Create(userEntity);
        } catch (Exception error) {
            logger.log(Level.SEVERE, "can not save user " + credentials.Login + "to storage." + error.getMessage());
            return ResultsFactory.FailedWith(AuthError.UnknownError);
        }
        return ResultsFactory.Success();
    }

    @Override
    public ValueResult<Pair<UUID, String>, AuthError> Login() {
        return null;
    }

    @Override
    public ValueResult<Boolean, AuthError> NotAuthorized(String authToken, UUID userId) {
        return null;
    }

    @Override
    public ValueResult<Boolean, AuthError> NotHaveAccess(UUID userId, UUID documentId) {
        return null;
    }
}
