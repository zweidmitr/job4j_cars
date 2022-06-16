package ru.job4j.car.service;

import org.springframework.stereotype.Service;
import ru.job4j.car.model.Body;
import ru.job4j.car.persistence.BodyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BodyService {
    private final BodyRepository store;

    public BodyService(BodyRepository store) {
        this.store = store;
    }

    public Optional<Body> add(Body body) {
        return store.add(body);
    }

    public void delete(int id) {
        store.delete(id);
    }

    public List<Body> findAll() {
        return store.findAll();
    }

    public Body findById(int id) {
        return store.findById(id);
    }
}
