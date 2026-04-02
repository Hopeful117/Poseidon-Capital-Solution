package com.nnk.controllers;

import com.nnk.domain.CurvePoint;
import com.nnk.services.CrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controleur MVC pour la gestion des points de courbe.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class CurveController {

    private final CrudService<CurvePoint> service;

    /**
     * Affiche la liste des points de courbe.
     *
     * @param model modele de vue
     * @return vue de liste
     */
    @GetMapping("/curvePoint/list")
    public String home(Model model) {
        List<CurvePoint> curvePoints = service.findAll();
        model.addAttribute("curvePoints", curvePoints);
        return "curvePoint/list";
    }

    /**
     * Affiche le formulaire d'ajout d'un point de courbe.
     *
     * @param curvePoint objet de formulaire
     * @return vue d'ajout
     */
    @GetMapping("/curvePoint/add")
    public String addCurveForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    /**
     * Valide et cree un point de courbe.
     *
     * @param curvePoint donnees soumises
     * @param result     resultat de validation
     * @param model      modele de vue
     * @return redirection vers la liste ou retour formulaire
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {

            return "curvePoint/add";
        }
        try {
            service.create(curvePoint);
            return "redirect:/curvePoint/list";
        } catch (Exception e) {
            log.error("Echec creation curvePoint curveId={}", curvePoint.getCurveId(), e);
            model.addAttribute("errorMessage", e.getMessage());
            return "curvePoint/add";
        }
    }

    /**
     * Affiche le formulaire de mise a jour.
     *
     * @param id                 identifiant du point
     * @param model              modele de vue
     * @param redirectAttributes attributs flash en cas d'erreur
     * @return vue de mise a jour ou redirection
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            CurvePoint curvePoint = service.findById(id);
            model.addAttribute("curvePoint", curvePoint);
            return "curvePoint/update";
        } catch (Exception e) {
            log.warn("Impossible de charger curvePoint id={} - {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/curvePoint/list";
        }
    }

    /**
     * Met a jour un point de courbe existant.
     *
     * @param id                 identifiant du point
     * @param curvePoint         donnees mises a jour
     * @param result             resultat de validation
     * @param model              modele de vue
     * @param redirectAttributes attributs flash pour erreurs de validation
     * @return redirection vers la liste ou vers le formulaire de mise a jour
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                              BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            redirectAttributes.addFlashAttribute("errorMessage", errors);
            return "redirect:/curvePoint/update/" + id;
        }
        try {
            curvePoint.setId(id);
            service.update(curvePoint);
            return "redirect:/curvePoint/list";
        } catch (Exception e) {
            log.error("Echec mise a jour curvePoint id={}", id, e);
            model.addAttribute("errorMessage", e.getMessage());
            return "curvePoint/update";
        }
    }

    /**
     * Supprime un point de courbe.
     *
     * @param id                 identifiant du point
     * @param redirectAttributes attributs flash en cas d'erreur
     * @return redirection vers la liste
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            service.deleteById(id);
        } catch (Exception e) {
            log.warn("Echec suppression curvePoint id={} - {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/curvePoint/list";
    }
}
