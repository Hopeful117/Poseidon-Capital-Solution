package com.nnk.config;

import com.nnk.exceptions.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
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
    public String handleEntityNotFoundException(EntityNotFoundException ex, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());


        if (referer != null && referer.contains("/user/")) {
            return "redirect:/user/list";
        } else if (referer != null && referer.contains("/rating/")) {
            return "redirect:/rating/list";
        } else if (referer != null && referer.contains("/bid/")) {
            return "redirect:/bid/list";
        } else if (referer != null && referer.contains("/curve/")) {
            return "redirect:/curve/list";
        } else if (referer != null && referer.contains("/trade/")) {
            return "redirect:/trade/list";
        } else if (referer != null && referer.contains("/rule/")) {
            return "redirect:/rule/list";
        }
        return "redirect:/";
    }
}



