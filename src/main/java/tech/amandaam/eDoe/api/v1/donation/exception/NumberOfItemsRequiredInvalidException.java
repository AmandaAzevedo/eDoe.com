package tech.amandaam.eDoe.api.v1.donation.exception;

public class NumberOfItemsRequiredInvalidException extends RuntimeException {
    public NumberOfItemsRequiredInvalidException(String msg){
        super(msg);
    }
}
