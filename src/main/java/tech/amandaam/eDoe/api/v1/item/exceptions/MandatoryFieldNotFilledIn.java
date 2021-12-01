package tech.amandaam.eDoe.api.v1.item.exceptions;

public class MandatoryFieldNotFilledIn  extends RuntimeException {
    public MandatoryFieldNotFilledIn(String msg){
        super(msg);
    }
}
