package com.nnk.config;

import com.nnk.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
/**
 * Service de chargement des utilisateurs pour Spring Security.
 */
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;


    /**
     * Charge un utilisateur par son nom pour l'authentification.
     *
     * @param username nom d'utilisateur de connexion
     * @return les informations de securite de l'utilisateur
     * @throws UsernameNotFoundException si l'utilisateur n'existe pas
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.nnk.domain.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Tentative de connexion avec un utilisateur inconnu username={}", username);
                    return new UsernameNotFoundException("User not found");
                });

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
