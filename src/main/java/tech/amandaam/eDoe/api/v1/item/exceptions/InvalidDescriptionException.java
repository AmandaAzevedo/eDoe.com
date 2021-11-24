package tech.amandaam.eDoe.api.v1.item.exceptions;

public class InvalidDescriptionException  extends RuntimeException {
    public InvalidDescriptionException(String msg){
        super(msg);
    }
}
