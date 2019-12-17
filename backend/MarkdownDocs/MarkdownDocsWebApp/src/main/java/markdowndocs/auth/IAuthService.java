package markdowndocs.auth;

import java.util.UUID;

public interface IAuthService {

    public boolean NotAuthorized(String authToken, UUID userId);

    public boolean NotHaveAccess(UUID userId, UUID documentId);
}

