package com.nnk.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
/**
 * Configuration Spring Security de l'application.
 * Definit les regles d'acces, la page de connexion et les composants d'authentification.
 */
public class SpringSecurityConfig {

    @Autowired
    private final CustomUserDetailsService userDetailsService;


    /**
     * Configure la chaine de filtres HTTP securisee.
     *
     * @param http configuration HTTP
     * @param successHandler gestionnaire appele apres une connexion reussie
     * @return la chaine de filtres de securite
     * @throws Exception en cas d'erreur de configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomAuthenticationSuccessHandler successHandler) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/app/login", "/css/**", "/js/**", "/images/**", "/", "/error").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("ADMIN")
                        .anyRequest().authenticated()

                )
                .formLogin(form -> form
                        .loginPage("/app/login")
                        .usernameParameter("username")
                        .successHandler(successHandler)
                        .permitAll()
                ).logout((logout) -> logout
                        .logoutUrl("/app/logout")
                        .logoutSuccessUrl("/app/login?logout")
                        .permitAll()
                ).exceptionHandling((ex ->
                        ex.authenticationEntryPoint((request, response, authException) ->
                        {
                            response.sendRedirect("/app/login");
                        }).accessDeniedHandler(((request, response, accessDeniedException) ->
                        {
                            response.sendRedirect("/app/error");
                        }))
                ));


        return http.build();
    }

    /**
     * Expose l'encodeur de mot de passe BCrypt.
     *
     * @return l'encodeur BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Cree le gestionnaire d'authentification base sur le service utilisateur et BCrypt.
     *
     * @param http configuration HTTP
     * @param passwordEncoder encodeur de mot de passe
     * @return le gestionnaire d'authentification
     * @throws Exception en cas d'erreur d'initialisation
     */
    @Bean
    public AuthenticationManager authentificationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();


    }

}
