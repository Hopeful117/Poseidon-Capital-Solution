package com.nnk.config;

import com.nnk.exceptions.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Gestionnaire global d'exceptions pour l'application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gere les exceptions de type EntityNotFoundException.
     * Ajoute un message flash puis redirige selon l'origine de la requete.
     *
     * @param ex exception interceptee
     * @param redirectAttributes attributs flash pour la redirection
     * @param model modele de vue
     * @param request requete HTTP courante
     * @return redirection vers la liste concernee ou vers l'accueil
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



