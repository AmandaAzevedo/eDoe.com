package tech.amandaam.eDoe.api.v1.user.exception;

public class InvalidNumberOfCaractersException extends RuntimeException{
    public InvalidNumberOfCaractersException(String msg){
        super(msg);
    }
}
