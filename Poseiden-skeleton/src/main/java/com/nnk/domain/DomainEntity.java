package com.nnk.domain;

public interface DomainEntity<M extends DomainEntity<M>> {

    Integer getId();

    M update(M domainEntity);
}
