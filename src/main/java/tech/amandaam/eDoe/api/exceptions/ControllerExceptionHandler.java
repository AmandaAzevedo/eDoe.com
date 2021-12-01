package tech.amandaam.eDoe.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.amandaam.eDoe.api.v1.request.exception.RequestNotExistException;
import tech.amandaam.eDoe.api.v1.descriptor.exception.DescriptorAlreadyExistsException;
import tech.amandaam.eDoe.api.v1.descriptor.exception.DescriptorOptionDoesNotExist;
import tech.amandaam.eDoe.api.v1.donation.exception.NumberOfItemsRequiredInvalidException;
import tech.amandaam.eDoe.api.v1.item.exceptions.InvalidDescriptionException;
import tech.amandaam.eDoe.api.v1.item.exceptions.ItemNotFoundException;
import tech.amandaam.eDoe.api.v1.item.exceptions.MandatoryFieldNotFilledIn;
import tech.amandaam.eDoe.api.v1.jwt.exception.LoginOrPasswordInvalideException;
import tech.amandaam.eDoe.api.v1.jwt.exception.PermissionDeniedException;
import tech.amandaam.eDoe.api.v1.jwt.exception.TokenDoesNotExistOrPoorlyFormatted;
import tech.amandaam.eDoe.api.v1.jwt.exception.UserNotExistException;
import tech.amandaam.eDoe.api.v1.user.exception.InvalidNumberOfCaractersException;
import tech.amandaam.eDoe.api.v1.user.exception.InvalidUserCategoryException;
import tech.amandaam.eDoe.api.v1.user.exception.UserAlreadyExistsException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<StandardError> userAlreadyExistsException(UserAlreadyExistsException e) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(InvalidNumberOfCaractersException.class)
    public ResponseEntity<StandardError> invalidNumberOfCaractersException(InvalidNumberOfCaractersException e) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(InvalidUserCategoryException.class)
    public ResponseEntity<StandardError> invalidUserCategoryException(InvalidUserCategoryException e) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(LoginOrPasswordInvalideException.class)
    public ResponseEntity<StandardError> loginOrPasswordInvalideException(LoginOrPasswordInvalideException e) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<StandardError> permissionDeniedException(PermissionDeniedException e) {
        StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<StandardError> userNotExistException(UserNotExistException e) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(RequestNotExistException.class)
    public ResponseEntity<StandardError> requestNotExistException(RequestNotExistException e) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DescriptorAlreadyExistsException.class)
    public ResponseEntity<StandardError> descriptorAlreadyExistsException(DescriptorAlreadyExistsException e) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(DescriptorOptionDoesNotExist.class)
    public ResponseEntity<StandardError> descriptorOptionDoesNotExist(DescriptorOptionDoesNotExist e) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(TokenDoesNotExistOrPoorlyFormatted.class)
    public ResponseEntity<StandardError> tokenDoesNotExistOrPoorlyFormatted(TokenDoesNotExistOrPoorlyFormatted e) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<StandardError> itemNotFoundException(ItemNotFoundException e) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(InvalidDescriptionException.class)
    public ResponseEntity<StandardError> invalidDescriptionException(InvalidDescriptionException e) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MandatoryFieldNotFilledIn.class)
    public ResponseEntity<StandardError> mandatoryFieldNotFilledIn(MandatoryFieldNotFilledIn e) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(NumberOfItemsRequiredInvalidException.class)
    public ResponseEntity<StandardError> numberOfItemsRequiredInvalidException(NumberOfItemsRequiredInvalidException e) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
