package markdowndocs.web.app.MarkdownDocsWebApp;

import markdowndocs.auth.AuthCredentials;
import markdowndocs.auth.AuthError;
import markdowndocs.auth.IAuthService;
import markdowndocs.infrastructure.Result;
import markdowndocs.infrastructure.ValueResult;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity Registry(@RequestBody RegistryRequestBody registryRequestBody) {

        AuthCredentials authCredentials = new AuthCredentials();

        authCredentials.Login = registryRequestBody.login;
        authCredentials.Password = registryRequestBody.password;

        Result<AuthError> result = authService.Registry(authCredentials);

        if (result.isSuccess())
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        AuthError error = result.getError();

        if (error == AuthError.AlreadyExist)
            return new ResponseEntity(HttpStatus.CONFLICT);

        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponseBody> Registry(@RequestBody LoginRequestBody loginRequestBody) {

        AuthCredentials authCredentials = new AuthCredentials();

        authCredentials.Login = loginRequestBody.login;
        authCredentials.Password = loginRequestBody.password;

        ValueResult<Pair<UUID, String>, AuthError> result = authService.Login(authCredentials);

        if (result.isSuccess()) {
            Pair<UUID, String> value = result.getValue();
            LoginResponseBody responseBody = new LoginResponseBody();
            responseBody.setUserId(value.getValue(0).toString());
            responseBody.setAuth(value.getValue(1).toString());

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
        AuthError error = result.getError();

        if (error == AuthError.UnknownError)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}

