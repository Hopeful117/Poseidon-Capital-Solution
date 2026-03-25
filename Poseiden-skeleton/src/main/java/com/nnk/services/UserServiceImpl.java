package com.nnk.services;

import com.nnk.domain.User;
import com.nnk.exceptions.EntityNotFoundException;
import com.nnk.repositories.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserServiceImpl extends AbstractCrudService<User> {
    protected UserServiceImpl(UserRepository repository) {
        super(repository);
    }
    @Override
    public void create (User user){
        Assert.notNull(user, "Objet is null");
        Assert.isNull(user.getId() , "Id can not be defined");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        repository.save(user);
    }

    @Override
    public void update (User user){
        Assert.notNull(user, "Objet is null");
        Assert.notNull(user.getId() , "Id can not be null");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        User oldUser= repository.findById(user.getId())
                .orElseThrow(()-> new EntityNotFoundException("Id not found"));
        User newUser=oldUser.update(user);

        repository.save(newUser);
    }
}
