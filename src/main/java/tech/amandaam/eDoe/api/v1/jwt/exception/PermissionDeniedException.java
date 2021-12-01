package tech.amandaam.eDoe.api.v1.jwt.exception;

public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException(String msg){
        super(msg);
    }

}
