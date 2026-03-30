package com.nnk.services;

import java.util.List;

public interface CrudService<M> {

    M findById(Integer id);

    List<M> findAll();

    void deleteById(Integer id);

    void create(M model);

    void update(M model);

}
