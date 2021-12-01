package tech.amandaam.eDoe.api.v1.jwt.exception;


public class LoginOrPasswordInvalideException extends RuntimeException {
    public LoginOrPasswordInvalideException(String msg){
        super(msg);
    }

}
