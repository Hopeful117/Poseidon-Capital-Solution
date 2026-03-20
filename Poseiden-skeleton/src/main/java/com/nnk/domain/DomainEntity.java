package com.nnk.domain;

public interface DomainEntity<M extends DomainEntity<?>> {

    Integer getId();

    M update(M domainEntity);
}
