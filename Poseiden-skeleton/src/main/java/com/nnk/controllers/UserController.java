package com.nnk.controllers;

import com.nnk.domain.User;
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

/**
 * Contrôleur pour la gestion des utilisateurs.
 * Gère les opérations CRUD et la navigation des pages utilisateur.
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    /** Service CRUD pour les utilisateurs */
    private final CrudService<User> service;

    /**
     * Affiche la liste de tous les utilisateurs.
     *
     * @param model le modèle pour passer les données à la vue
     * @return le nom de la vue "user/list"
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", service.findAll());
        return "user/list";
    }

    /**
     * Affiche le formulaire pour ajouter un nouvel utilisateur.
     *
     * @param bid l'objet utilisateur (optionnel)
     * @return le nom de la vue "user/add"
     */
    @GetMapping("/user/add")
    public String addUser(User bid) {
        return "user/add";
    }

    /**
     * Valide et crée un nouvel utilisateur.
     *
     * @param user l'utilisateur à créer
     * @param result les erreurs de validation
     * @param model le modèle pour passer les données à la vue
     * @return une redirection vers la liste ou le formulaire avec erreurs
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {

            return "user/add";
        }
        try {

            service.create(user);
            return "redirect:/user/list";
        } catch (Exception e) {
            model.addAttribute("errors", e.getMessage());
            return "user/add";
        }


    }

    /**
     * Affiche le formulaire pour mettre à jour un utilisateur.
     *
     * @param id l'identifiant de l'utilisateur
     * @param model le modèle pour passer les données à la vue
     * @param redirectAttributes les attributs de redirection
     * @return le nom de la vue "user/update" ou une redirection si l'utilisateur n'existe pas
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = service.findById(id);
            user.setPassword("");
            model.addAttribute("user", user);
            return "user/update";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/user/list";
        }
    }

    /**
     * Valide et met à jour un utilisateur existant.
     *
     * @param id l'identifiant de l'utilisateur
     * @param user l'utilisateur avec les données mises à jour
     * @param result les erreurs de validation
     * @param model le modèle pour passer les données à la vue
     * @return une redirection vers la liste ou le formulaire avec erreurs
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {

            return "user/update";
        }

        try {

            user.setId(id);
            service.update(user);

            return "redirect:/user/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("user", user);
            return "user/update";
        }
    }

    /**
     * Supprime un utilisateur.
     *
     * @param id l'identifiant de l'utilisateur à supprimer
     * @param model le modèle pour passer les données à la vue
     * @param redirectAttributes les attributs de redirection
     * @return une redirection vers la liste des utilisateurs
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            service.deleteById(id);


        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());

        }
        return "redirect:/user/list";
    }
}
