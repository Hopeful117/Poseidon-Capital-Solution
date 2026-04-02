package com.nnk.controllers;

import com.nnk.domain.BidList;
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
 * Controleur MVC pour la gestion des bid lists.
 */
@RequiredArgsConstructor
@Controller
@Slf4j
public class BidListController {


    private final CrudService<BidList> service;

    /**
     * Affiche la liste des bid lists.
     *
     * @param model modele de vue
     * @return vue de liste
     */
    @GetMapping("/bidList/list")
    public String home(Model model) {

        List<BidList> bidLists = service.findAll();
        model.addAttribute("bidLists", bidLists);
        return "bidList/list";
    }

    /**
     * Affiche le formulaire d'ajout.
     *
     * @param bid objet de formulaire
     * @return vue d'ajout
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    /**
     * Valide et cree une bid list.
     *
     * @param bid    donnees soumises
     * @param result resultat de validation
     * @param model  modele de vue
     * @return redirection vers la liste ou retour formulaire
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {

        if (result.hasErrors()) {

            return "bidList/add";
        }
        try {
            service.create(bid);
            return "redirect:/bidList/list";
        } catch (Exception e) {
            log.error("Echec creation bidList pour account={}", bid.getAccount(), e);
            model.addAttribute("errorMessage", e.getMessage());
            return "bidList/add";
        }

    }

    /**
     * Affiche le formulaire de modification d'une bid list.
     *
     * @param id                 identifiant de la bid list
     * @param model              modele de vue
     * @param redirectAttributes attributs flash en cas d'erreur
     * @return vue de mise a jour ou redirection
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {

        try {
            BidList bidList = service.findById(id);
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        } catch (Exception e) {
            log.warn("Impossible de charger bidList id={} - {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/bidList/list";
        }
    }

    /**
     * Met a jour une bid list existante.
     *
     * @param id      identifiant de la bid list
     * @param bidList donnees mises a jour
     * @param result  resultat de validation
     * @param model   modele de vue
     * @return redirection vers la liste ou retour formulaire
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {

        if (result.hasErrors()) {

            return "bidList/update";
        }
        try {
            bidList.setBidListId(id);
            service.update(bidList);
            return "redirect:/bidList/list";
        } catch (Exception e) {
            log.error("Echec mise a jour bidList id={}", id, e);
            model.addAttribute("errorMessage", e.getMessage());
            return "bidList/update";
        }
    }

    /**
     * Supprime une bid list.
     *
     * @param id                 identifiant de la bid list
     * @param redirectAttributes attributs flash en cas d'erreur
     * @return redirection vers la liste
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        try {
            service.deleteById(id);

        } catch (Exception e) {
            log.warn("Echec suppression bidList id={} - {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/bidList/list";
    }
}
