package tech.amandaam.eDoe.api.v1.descriptor.exception;

public class DescriptorAlreadyExistsException extends RuntimeException {
    public DescriptorAlreadyExistsException(String msg){
        super(msg);
    }

}