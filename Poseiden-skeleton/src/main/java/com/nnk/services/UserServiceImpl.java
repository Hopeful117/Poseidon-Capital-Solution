package com.nnk.services;

import com.nnk.domain.User;
import com.nnk.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserServiceImpl extends AbstractCrudService<User> {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    protected UserServiceImpl(UserRepository repository) {
        super(repository);

    }

    @Override
    public void create(final User user) {
        encryptPassword(user);
        super.create(user);
    }

    @Override
    public void update(final User user) {
        encryptPassword(user);
        super.update(user);
    }

    private void encryptPassword(User user) {
        Assert.notNull(user, "Objet is null");
        user.setPassword(encoder.encode(user.getPassword()));
    }
}
