package tech.amandaam.eDoe.api.v1.descriptor.exception;

public class DescriptorDoesNotExistException extends RuntimeException {
    public DescriptorDoesNotExistException(String msg){
        super(msg);
    }
}
