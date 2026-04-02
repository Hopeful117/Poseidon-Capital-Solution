package com.nnk.controllers;

import com.nnk.domain.Rating;
import com.nnk.services.CrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controleur MVC pour la gestion des ratings.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class RatingController {


    private final CrudService<Rating> service;

    /**
     * Affiche la liste des ratings.
     *
     * @param model modele de vue
     * @return vue de liste
     */
    @GetMapping("/rating/list")
    public String home(Model model) {
        List<Rating> ratings = service.findAll();
        model.addAttribute("ratings", ratings);

        return "rating/list";
    }

    /**
     * Affiche le formulaire d'ajout d'un rating.
     *
     * @param rating objet de formulaire
     * @return vue d'ajout
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
     * Valide et cree un rating.
     *
     * @param rating donnees soumises
     * @param result resultat de validation
     * @param model  modele de vue
     * @return redirection vers la liste ou retour formulaire
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {

        if (result.hasErrors()) {

            return "rating/add";
        }
        try {

            service.create(rating);
            return "redirect:/rating/list";
        } catch (Exception e) {
            log.error("Echec creation rating orderNumber={}", rating.getOrderNumber(), e);
            model.addAttribute("errorMessage", e.getMessage());
            return "rating/add";
        }

    }

    /**
     * Affiche le formulaire de mise a jour d'un rating.
     *
     * @param id                 identifiant du rating
     * @param model              modele de vue
     * @param redirectAttributes attributs flash en cas d'erreur
     * @return vue de mise a jour ou redirection
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {

        try {
            Rating rating = service.findById(id);
            model.addAttribute("rating", rating);
            return "rating/update";
        } catch (Exception e) {
            log.warn("Impossible de charger rating id={} - {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/rating/list";
        }
    }

    /**
     * Met a jour un rating existant.
     *
     * @param id     identifiant du rating
     * @param rating donnees mises a jour
     * @param result resultat de validation
     * @param model  modele de vue
     * @return redirection vers la liste ou retour formulaire
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {

        if (result.hasErrors()) {

            return "rating/update";
        }
        try {

            rating.setId(id);
            service.update(rating);
            return "redirect:/rating/list";
        } catch (Exception e) {
            log.error("Echec mise a jour rating id={}", id, e);
            model.addAttribute("errorMessage", e.getMessage());
            return "rating/update";
        }
    }

    /**
     * Supprime un rating.
     *
     * @param id                 identifiant du rating
     * @param redirectAttributes attributs flash en cas d'erreur
     * @return redirection vers la liste
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        try {
            service.deleteById(id);

        } catch (Exception e) {
            log.warn("Echec suppression rating id={} - {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/rating/list";
    }
}
