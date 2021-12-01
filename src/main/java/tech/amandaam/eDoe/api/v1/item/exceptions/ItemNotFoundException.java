package tech.amandaam.eDoe.api.v1.item.exceptions;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String msg){
        super(msg);
    }
}
