package com.nnk.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * Redirige l'utilisateur apres connexion selon son role.
 */
@Component
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * Redirection post-authentification vers l'espace admin ou utilisateur.
     *
     * @param request        requete HTTP
     * @param response       reponse HTTP
     * @param authentication contexte d'authentification
     * @throws IOException en cas d'erreur d'ecriture de la reponse
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String redirectUrl = "/";

        if (authentication.getAuthorities().stream()
                .anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_ADMIN"))) {
            redirectUrl = "/admin/home";
        } else if (authentication.getAuthorities().stream()
                .anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_USER"))) {
            redirectUrl = "/bidList/list";
        }

        log.info("Connexion reussie user={} redirect={}", authentication.getName(), redirectUrl);
        response.sendRedirect(redirectUrl);
    }
}
