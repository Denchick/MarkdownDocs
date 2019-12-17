package markdowndocs.auth;

import java.util.UUID;

public interface IAuthService {

    public boolean NotAuthorized(String authToken, UUID userId);

    public boolean HaveAccess(UUID userId, UUID documentId);
}

