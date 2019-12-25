package markdowndocs.auth;

import markdowndocs.infrastructure.Result;
import markdowndocs.infrastructure.ResultsFactory;
import markdowndocs.infrastructure.ValueResult;
import org.javatuples.Pair;

import java.util.UUID;

public class FakeAuthService implements IAuthService {

    @Override
    public Result<AuthError> Registry(AuthCredentials credentials) {

        return ResultsFactory.Success();
    }

    @Override
    public ValueResult<Pair<UUID, String>, AuthError> Login(AuthCredentials credentials) {
        return ResultsFactory.Success(new Pair<>(UUID.fromString("9982ea38-d1a2-4bba-aa47-7238a0109dbb"), "token"));
    }

    @Override
    public boolean Authorized(String authToken, UUID userId) {
        return true;
    }

    @Override
    public boolean HaveAccess(UUID userId, UUID documentId) {
        return true;
    }
}
