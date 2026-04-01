package com.nnk.controllers;

import com.nnk.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controleur des pages de connexion et d'erreur d'acces.
 */
@Controller
@RequestMapping("app")
@RequiredArgsConstructor
public class LoginController {


    private final UserRepository userRepository;

    /**
     * Affiche la page de connexion.
     *
     * @param model modele de vue
     * @return vue login
     */
    @GetMapping("/login")
    public String login(Model model) {

        model.addAttribute("viewName", "login");
        return "login";
    }

    /**
     * Affiche la liste des utilisateurs pour la zone securisee.
     *
     * @param model modele de vue
     * @return vue liste utilisateur
     */
    @GetMapping("/secure/article-details")
    public String getAllUserArticles(Model model) {

        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("viewName", "user/list");
        return "user/list";
    }

    /**
     * Affiche la page d'erreur d'autorisation.
     *
     * @param model modele de vue
     * @return vue 403
     */
    @GetMapping("/error")
    public String error(Model model) {

        String errorMessage = "You are not authorized for the requested data.";
        model.addAttribute("errorMsg", errorMessage);
        model.addAttribute("viewName", "403");
        return "403";
    }


}
