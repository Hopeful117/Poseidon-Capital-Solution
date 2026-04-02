package com.nnk.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controleur des pages d'accueil.
 */
@Controller
public class HomeController {
    /**
     * Affiche la page d'accueil publique.
     *
     * @return vue index
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /**
     * Redirige l'administrateur vers la liste des utilisateurs.
     *
     * @return redirection vers l'espace admin
     */
    @GetMapping("/admin/home")
    public String adminHome() {
        return "redirect:/user/list";
    }


}
