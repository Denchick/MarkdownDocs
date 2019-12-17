package markdowndocs.auth;

import java.util.UUID;

public class FakeAuthService implements IAuthService {

    @Override
    public boolean NotAuthorized(String authToken, UUID userId) {
        return true;
    }

    @Override
    public boolean HaveAccess(UUID userId, UUID documentId) {
        return true;
    }
}
