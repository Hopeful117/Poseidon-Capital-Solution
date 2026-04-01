package com.nnk.exceptions;

/**
 * Exception levée lorsqu'une entité n'est pas trouvée par son ID.
 * Cette exception est utilisée pour signaler une tentative d'accès à une entité inexistante.
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructeur avec message d'erreur.
     *
     * @param message le message d'erreur décrivant la raison
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructeur avec message d'erreur et cause.
     *
     * @param message le message d'erreur décrivant la raison
     * @param cause la cause originale de l'exception
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

