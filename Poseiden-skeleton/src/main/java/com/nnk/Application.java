package com.nnk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entree de l'application Spring Boot Poseiden.
 */
@SpringBootApplication
public class Application {

    /**
     * Lance le contexte Spring Boot.
     *
     * @param args arguments de ligne de commande
     */
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}
