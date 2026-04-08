package com.nnk.exceptions;

/**
 * Exception levée lorsqu'une tentative de création d'utilisateur est effectuée avec un nom d'utilisateur déjà existant.
 * Cette exception est utilisée pour signaler une violation de l'unicité du nom d'utilisateur lors de la création d'un nouvel utilisateur.
 */

public class UsernameAlreadyInUseException extends RuntimeException {
    public UsernameAlreadyInUseException(String message) {
        super(message);
    }
}
