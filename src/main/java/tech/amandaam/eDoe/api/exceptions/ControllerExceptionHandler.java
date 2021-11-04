package tech.amandaam.eDoe.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.amandaam.eDoe.api.v1.jwt.exception.LoginOrPasswordInvalideException;
import tech.amandaam.eDoe.api.v1.user.exception.InvalidNumberOfCaractersException;
import tech.amandaam.eDoe.api.v1.user.exception.InvalidUserCategoryException;
import tech.amandaam.eDoe.api.v1.user.exception.UserAlreadyExistsException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<StandardError> disciplineNotFoundException(UserAlreadyExistsException e) {
        StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(InvalidNumberOfCaractersException.class)
    public ResponseEntity<StandardError> invalidNumberOfCaractersException(InvalidNumberOfCaractersException e) {
        StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(InvalidUserCategoryException.class)
    public ResponseEntity<StandardError> invalidUserCategoryException(InvalidUserCategoryException e) {
        StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(LoginOrPasswordInvalideException.class)
    public ResponseEntity<StandardError> disciplineNotFoundException(LoginOrPasswordInvalideException e) {
        StandardError err = new StandardError(HttpStatus.OK.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.OK).body(err);
    }
}
