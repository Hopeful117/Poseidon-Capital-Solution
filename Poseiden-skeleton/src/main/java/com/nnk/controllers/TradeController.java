package com.nnk.controllers;

import com.nnk.domain.Trade;
import com.nnk.services.CrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controleur MVC pour la gestion des trades.
 */
@RequiredArgsConstructor
@Controller
public class TradeController {


    private final CrudService<Trade> service;

    // TODO: Inject Trade service

    /**
     * Affiche la liste des trades.
     *
     * @param model modele de vue
     * @return vue de liste
     */
    @RequestMapping("/trade/list")
    public String home(Model model) {
        List<Trade> trades = service.findAll();
        model.addAttribute("trades", trades);
        return "trade/list";
    }

    /**
     * Affiche le formulaire d'ajout d'un trade.
     *
     * @param bid objet de formulaire
     * @return vue d'ajout
     */
    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

    /**
     * Valide et cree un trade.
     *
     * @param trade donnees soumises
     * @param result resultat de validation
     * @param model modele de vue
     * @return redirection vers la liste ou retour formulaire
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {

            return "trade/add";
        }
        try {
            service.create(trade);
            return "redirect:/trade/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "trade/add";
        }
    }

    /**
     * Affiche le formulaire de mise a jour d'un trade.
     *
     * @param id identifiant du trade
     * @param model modele de vue
     * @param redirectAttributes attributs flash en cas d'erreur
     * @return vue de mise a jour ou redirection
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Trade trade = service.findById(id);
            model.addAttribute("trade", trade);
            return "trade/update";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/trade/list";
        }
    }

    /**
     * Met a jour un trade existant.
     *
     * @param id identifiant du trade
     * @param trade donnees mises a jour
     * @param result resultat de validation
     * @param model modele de vue
     * @return redirection vers la liste ou retour formulaire
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {

            return "trade/update";
        }
        try {
            trade.setTradeId(id);
            service.update(trade);
            return "redirect:/trade/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "trade/update";
        }
    }

    /**
     * Supprime un trade.
     *
     * @param id identifiant du trade
     * @param redirectAttributes attributs flash en cas d'erreur
     * @return redirection vers la liste
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            service.deleteById(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/trade/list";
    }
}
