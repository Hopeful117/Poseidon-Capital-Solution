package com.nnk.services;

import com.nnk.domain.DomainEntity;
import com.nnk.exceptions.EntityNotFoundException;
import jakarta.validation.constraints.AssertTrue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudService<M extends DomainEntity<M>> implements CrudService<M> {


    protected final JpaRepository<M, Integer> repository;
    
    protected AbstractCrudService(JpaRepository<M, Integer> repository ){
        this.repository= repository;
    }

    @Override
    public M findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id not found"));
    }

    @Override
    public List<M> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Id not found"));
        repository.deleteById(id);
    }

    @Override
    public void create(M model) {
        Assert.notNull(model, "Objet is null");
        Assert.isNull(model.getId() , "Id can not be defined");

        repository.save(model);
    }

    @Override
    public void update(M model) {
        Assert.notNull(model, "Objet is null");
        Assert.notNull(model.getId() , "Id can not be null");

       M oldModel= repository.findById(model.getId())
                .orElseThrow(()-> new EntityNotFoundException("Id not found"));
       M newModel=oldModel.update(model);

        repository.save(newModel);
    }
}
