package tech.amandaam.eDoe.api.v1.jwt.exception;

public class TokenDoesNotExistOrPoorlyFormatted extends RuntimeException {
    public TokenDoesNotExistOrPoorlyFormatted(String msg){
        super(msg);
    }

}
