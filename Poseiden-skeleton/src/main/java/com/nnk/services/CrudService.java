package com.nnk.services;

import java.util.List;

/**
 * Interface générique pour les opérations CRUD (Create, Read, Update, Delete).
 * Définit le contrat pour les services de gestion des entités.
 *
 * @param <M> le type d'entité gérée
 */
public interface CrudService<M> {

    /**
     * Récupère une entité par son identifiant.
     *
     * @param id l'identifiant de l'entité
     * @return l'entité trouvée
     */
    M findById(Integer id);

    /**
     * Récupère toutes les entités.
     *
     * @return la liste de toutes les entités
     */
    List<M> findAll();

    /**
     * Supprime une entité par son identifiant.
     *
     * @param id l'identifiant de l'entité à supprimer
     */
    void deleteById(Integer id);

    /**
     * Crée une nouvelle entité.
     *
     * @param model l'entité à créer
     */
    void create(M model);

    /**
     * Met à jour une entité existante.
     *
     * @param model l'entité à mettre à jour
     */
    void update(M model);

}
