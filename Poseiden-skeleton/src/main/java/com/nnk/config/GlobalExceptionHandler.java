package com.nnk.config;

import com.nnk.exceptions.EntityNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Gestionnaire global d'exceptions pour l'application
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gère les exceptions EntityNotFoundException
     * Redirige avec un message d'erreur
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFoundException(EntityNotFoundException ex, RedirectAttributes redirectAttributes, Model model) {
        String referer = null;
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/";
    }
}

