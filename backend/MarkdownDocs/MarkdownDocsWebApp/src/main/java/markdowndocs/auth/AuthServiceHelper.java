package markdowndocs.auth;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class AuthServiceHelper {
    public static UUID CreateId(AuthCredentials credentials) {
        return UUID.nameUUIDFromBytes((credentials.Login + credentials.Password).getBytes());
    }

    public static String GenerateAuth(String login, String hashPassword) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(login.getBytes());
        md.update(hashPassword.getBytes());
        md.update(Long.toString(System.currentTimeMillis()).getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter
                .printHexBinary(digest).toLowerCase();
    }

}
