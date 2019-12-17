package markdowndocs.auth;

import java.util.UUID;

public class FakeAuthService implements IAuthService {

    @Override
    public boolean NotAuthorized(String authToken, UUID userId) {
        return false;
    }

    @Override
    public boolean NotHaveAccess(UUID userId, UUID documentId) {
        return false;
    }
}
