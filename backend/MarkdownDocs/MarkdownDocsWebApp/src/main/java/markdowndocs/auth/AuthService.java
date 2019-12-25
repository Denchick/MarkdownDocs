package markdowndocs.auth;

import markdowndocs.OrmPersistents.DocumentEntity;
import markdowndocs.OrmPersistents.UserEntity;
import markdowndocs.documentstorage.IQueryExecutor;
import markdowndocs.infrastructure.Result;
import markdowndocs.infrastructure.ResultsFactory;
import markdowndocs.infrastructure.ValueResult;
import org.javatuples.Pair;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AuthService implements IAuthService {
    private IQueryExecutor queryExecutor;
    private Logger logger;
    private IAuthValidator validator;
    private long threeDaysInMills = 3 * 3600 * 1000;

    public AuthService(IQueryExecutor queryExecutor, IAuthValidator authValidator, Logger logger) {
        this.queryExecutor = queryExecutor;
        this.validator = authValidator;
        this.logger = logger;
    }

    @Override
    public Result<AuthError> Registry(AuthCredentials credentials) {

        if (!validator.IsValidateCredentials(credentials)) {
            logger.log(Level.SEVERE, "Invalid credentials");
            return ResultsFactory.FailedWith(AuthError.BadCredentials);
        }
        UUID userId = AuthServiceHelper.CreateId(credentials);

        if (queryExecutor.EntityExist(userId)) {
            return ResultsFactory.FailedWith(AuthError.AlreadyExist);
        }

        UserEntity userEntity = UserEntity.CreateEntityWithPasswordHashing(credentials.Login, credentials.Password, userId);
        try {
            queryExecutor.Create(userEntity);
        } catch (Exception error) {
            logger.log(Level.SEVERE, "can not save user " + credentials.Login + "to storage." + error.getMessage());
            return ResultsFactory.FailedWith(AuthError.UnknownError);
        }
        return ResultsFactory.Success();
    }

    @Override
    public ValueResult<Pair<UUID, String>, AuthError> Login(AuthCredentials credentials) {
        UUID userId = AuthServiceHelper.CreateId(credentials);

        try {
            UserEntity userEntity = queryExecutor.GetEntityBy(userId, UserEntity.class);
            if (userEntity == null) {
                logger.log(Level.SEVERE, "Unknown user " + credentials.Login);
                return ResultsFactory.Failed(AuthError.BadCredentials);
            }
            String auth = AuthServiceHelper.GenerateAuth(userEntity.getLogin(), userEntity.getPasswordHash());
            Timestamp expireAt = new Timestamp(System.currentTimeMillis() + threeDaysInMills);
            userEntity.setAuthToken(auth);
            userEntity.setExpireAt(expireAt);
            queryExecutor.Update(userEntity);
            return ResultsFactory.Success(new Pair<>(userEntity.getId(), userEntity.getAuthToken()));

        } catch (Exception error) {
            logger.log(Level.SEVERE, "can not login " + credentials.Login + ". " + error.getMessage());
            return ResultsFactory.Failed(AuthError.UnknownError);
        }
    }

    @Override
    public boolean Authorized(String authToken, UUID userId) {

        try {
            UserEntity userEntity = queryExecutor.GetEntityBy(userId, UserEntity.class);

            if (userEntity == null)
                return false;
            if (userEntity.getAuthToken().equals(authToken)) {
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                return userEntity.getExpireAt().after(currentTimestamp);
            }
            return false;

        } catch (Exception error) {
            logger.log(Level.SEVERE, "Can not Authorized. Error: " + error.getMessage());
            return false;
        }

    }

    @Override
    public boolean HaveAccess(UUID userId, UUID documentId) {
        try {
            UserEntity userEntity = queryExecutor.GetEntityBy(userId, UserEntity.class);
            if (userEntity == null)
                return false;
            DocumentEntity documentEntity = queryExecutor.GetEntityBy(documentId, DocumentEntity.class);
            if (documentEntity == null)
                return true;

            return userEntity.getId() == documentEntity.getOwner();

        } catch (Exception error) {
            logger.log(Level.SEVERE, "Can not check access. Error" + error.getMessage());
            return true;
        }
    }
}
