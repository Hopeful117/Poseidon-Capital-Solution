package com.nnk.exceptions;

/**
 * Exception levée lorsqu'une entité n'est pas trouvée par son ID
 */
public class EntityNotFoundException extends RuntimeException {
    
    public EntityNotFoundException(String message) {
        super(message);
    }
    
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

