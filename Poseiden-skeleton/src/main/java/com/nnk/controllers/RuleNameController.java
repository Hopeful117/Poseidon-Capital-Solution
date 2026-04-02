package com.nnk.controllers;

import com.nnk.domain.RuleName;
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
 * Controleur MVC pour la gestion des regles metier.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class RuleNameController {


    private final CrudService<RuleName> service;

    /**
     * Affiche la liste des regles.
     *
     * @param model modele de vue
     * @return vue de liste
     */
    @GetMapping("/ruleName/list")
    public String home(Model model) {
        List<RuleName> ruleNames = service.findAll();
        model.addAttribute("ruleNames", ruleNames);
        return "ruleName/list";
    }

    /**
     * Affiche le formulaire d'ajout d'une regle.
     *
     * @param ruleName objet de formulaire
     * @return vue d'ajout
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        return "ruleName/add";
    }

    /**
     * Valide et cree une regle.
     *
     * @param ruleName donnees soumises
     * @param result   resultat de validation
     * @param model    modele de vue
     * @return redirection vers la liste ou retour formulaire
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {

            return "ruleName/add";
        }
        try {
            service.create(ruleName);
            return "redirect:/ruleName/list";
        } catch (Exception e) {
            log.error("Echec creation ruleName name={}", ruleName.getName(), e);
            model.addAttribute("errorMessage", e.getMessage());
            return "ruleName/add";
        }
    }

    /**
     * Affiche le formulaire de mise a jour d'une regle.
     *
     * @param id                 identifiant de la regle
     * @param model              modele de vue
     * @param redirectAttributes attributs flash en cas d'erreur
     * @return vue de mise a jour ou redirection
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            RuleName ruleName = service.findById(id);
            model.addAttribute("ruleName", ruleName);
            return "ruleName/update";
        } catch (Exception e) {
            log.warn("Impossible de charger ruleName id={} - {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/ruleName/list";
        }
    }

    /**
     * Met a jour une regle existante.
     *
     * @param id       identifiant de la regle
     * @param ruleName donnees mises a jour
     * @param result   resultat de validation
     * @param model    modele de vue
     * @return redirection vers la liste ou retour formulaire
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {

            return "ruleName/update";
        }
        try {
            ruleName.setId(id);
            service.update(ruleName);
            return "redirect:/ruleName/list";
        } catch (Exception e) {
            log.error("Echec mise a jour ruleName id={}", id, e);
            model.addAttribute("errorMessage", e.getMessage());
            return "ruleName/update";
        }
    }

    /**
     * Supprime une regle.
     *
     * @param id                 identifiant de la regle
     * @param redirectAttributes attributs flash en cas d'erreur
     * @return redirection vers la liste
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            service.deleteById(id);
        } catch (Exception e) {
            log.warn("Echec suppression ruleName id={} - {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/ruleName/list";
    }
}
