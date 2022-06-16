package ru.job4j.car.service;

import org.springframework.stereotype.Service;
import ru.job4j.car.model.User;
import ru.job4j.car.persistence.UserRepository;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository store;

    public UserService(UserRepository store) {
        this.store = store;
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public void delete(int id) {
        store.delete(id);
    }

    public List<User> findAll() {
        return store.findAll();
    }

    public Optional<User> findByEmail(String email, String password) {
        return store.findByEmail(email, password);
    }
}
