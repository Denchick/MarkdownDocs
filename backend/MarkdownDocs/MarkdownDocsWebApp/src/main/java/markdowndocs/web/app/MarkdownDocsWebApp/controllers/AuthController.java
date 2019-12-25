package markdowndocs.web.app.MarkdownDocsWebApp.controllers;

import markdowndocs.auth.AuthCredentials;
import markdowndocs.auth.AuthError;
import markdowndocs.auth.IAuthService;
import markdowndocs.infrastructure.Result;
import markdowndocs.infrastructure.ValueResult;
import markdowndocs.web.app.MarkdownDocsWebApp.models.LoginRequestBody;
import markdowndocs.web.app.MarkdownDocsWebApp.models.LoginResponseBody;
import markdowndocs.web.app.MarkdownDocsWebApp.models.RegistryRequestBody;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @RequestMapping(value="/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/{login}", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<LoginResponseBody> Login(@PathVariable String login, @RequestBody String password) {

        AuthCredentials authCredentials = new AuthCredentials();

        authCredentials.Login = login;
        authCredentials.Password = password;

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

