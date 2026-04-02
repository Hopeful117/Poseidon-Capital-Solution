package com.nnk.services;

import com.nnk.domain.DomainEntity;
import com.nnk.exceptions.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Service CRUD abstrait qui fournit les opérations de base sur les entités.
 * Cette classe générique gère la persistance des données via un repository JPA.
 *
 * @param <M> le type d'entité gérée (doit implémenter DomainEntity)
 */
public abstract class AbstractCrudService<M extends DomainEntity<M>> implements CrudService<M> {

    /**
     * Repository JPA pour accéder à la base de données
     */
    protected final JpaRepository<M, Integer> repository;

    /**
     * Constructeur protégé pour initialiser le repository.
     *
     * @param repository le repository JPA pour l'entité
     */
    protected AbstractCrudService(JpaRepository<M, Integer> repository) {
        this.repository = repository;
    }

    /**
     * Récupère une entité par son identifiant.
     *
     * @param id l'identifiant de l'entité
     * @return l'entité trouvée
     * @throws EntityNotFoundException si l'entité n'existe pas
     */
    @Override
    public M findById(final Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id not found"));
    }

    /**
     * Récupère toutes les entités.
     *
     * @return la liste de toutes les entités
     */
    @Override
    public List<M> findAll() {
        return repository.findAll();
    }

    /**
     * Supprime une entité par son identifiant.
     *
     * @param id l'identifiant de l'entité à supprimer
     * @throws EntityNotFoundException si l'entité n'existe pas
     */
    @Override
    public void deleteById(final Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Id not found");
        }

        repository.deleteById(id);
    }

    /**
     * Crée une nouvelle entité.
     *
     * @param model l'entité à créer (ne doit pas avoir d'id)
     * @throws IllegalArgumentException si l'objet est null ou possède un id
     */
    @Override
    public void create(final M model) {
        Assert.notNull(model, "Objet is null");
        Assert.isNull(model.getId(), "Id can not be defined");

        repository.save(model);
    }

    /**
     * Met à jour une entité existante.
     *
     * @param model l'entité à mettre à jour (doit avoir un id)
     * @throws IllegalArgumentException si l'objet est null ou n'a pas d'id
     * @throws EntityNotFoundException  si l'entité n'existe pas
     */
    @Override
    public void update(final M model) {
        Assert.notNull(model, "Objet is null");
        Assert.notNull(model.getId(), "Id can not be null");

        final M updatedModel = repository.findById(model.getId())
                .orElseThrow(() -> new EntityNotFoundException("Id not found"))
                .update(model);

        repository.save(updatedModel);
    }
}
