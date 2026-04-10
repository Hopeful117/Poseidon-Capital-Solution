package com.nnk.services;

import com.nnk.domain.User;
import com.nnk.exceptions.EntityNotFoundException;
import com.nnk.exceptions.UsernameAlreadyInUseException;
import com.nnk.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * Implémentation du service pour la gestion des utilisateurs.
 * Gère la création et la mise à jour des utilisateurs avec encryptage sécurisé des mots de passe.
 */
@Service
public class UserServiceImpl extends AbstractCrudService<User> {

    /**
     * Encoder BCrypt pour sécuriser les mots de passe
     */
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    /**
     * Constructeur pour initialiser le service avec le repository des utilisateurs.
     *
     * @param userRepository le repository pour accéder aux données des utilisateurs
     */
    protected UserServiceImpl(UserRepository userRepository, UserRepository userRepository1) {
        super(userRepository);


        this.userRepository = userRepository;
    }

    /**
     * Crée un nouvel utilisateur avec mot de passe encrypté.
     *
     * @param user l'utilisateur à créer
     */
    @Override
    public void create(final User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyInUseException("Username already in use");
        }
        encryptPassword(user);
        super.create(user);
    }

    /**
     * Met à jour un utilisateur existant avec mot de passe encrypté.
     *
     * @param user l'utilisateur à mettre à jour
     */
    @Override
    public void update(final User user) {
        userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("user not found"));

        userRepository.findByUsername(user.getUsername())
                .filter(existingUser -> !Objects.equals(existingUser.getId(), user.getId()))
                .ifPresent(existingUser -> {
                    throw new UsernameAlreadyInUseException("Username already in use");
                });

        encryptPassword(user);
        super.update(user);
    }

    /**
     * Encrypte le mot de passe d'un utilisateur.
     *
     * @param user l'utilisateur dont le mot de passe doit être encrypté
     * @throws IllegalArgumentException si l'utilisateur est null
     */
    private void encryptPassword(User user) {
        Assert.notNull(user, "Objet is null");
        user.setPassword(encoder.encode(user.getPassword()));
    }
}
