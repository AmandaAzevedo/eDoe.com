package tech.amandaam.eDoe.api.v1.user.exception;

public class InvalidUserCategoryException extends RuntimeException{
    public InvalidUserCategoryException(String msg){
        super(msg);
    }
}