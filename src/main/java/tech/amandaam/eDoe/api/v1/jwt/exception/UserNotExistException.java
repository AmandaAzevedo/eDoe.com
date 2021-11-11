package tech.amandaam.eDoe.api.v1.jwt.exception;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException(String msg){
        super(msg);
    }

}

