package com.nnk.domain;

/**
 * Contrat commun des entites metier manipulables par les services CRUD.
 *
 * @param <M> type concret de l'entite
 */
public interface DomainEntity<M extends DomainEntity<M>> {

    /**
     * Retourne l'identifiant technique de l'entite.
     *
     * @return identifiant de l'entite
     */
    Integer getId();

    /**
     * Copie les donnees metier d'une autre instance dans l'instance courante.
     *
     * @param domainEntity source des nouvelles valeurs
     * @return instance courante mise a jour
     */
    M update(M domainEntity);
}
