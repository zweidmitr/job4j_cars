package ru.job4j.car.service;

import org.springframework.stereotype.Service;
import ru.job4j.car.model.Engine;
import ru.job4j.car.persistence.EngineRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class EngineService {
    private final EngineRepository store;

    public EngineService(EngineRepository store) {
        this.store = store;
    }

    public Optional<Engine> add(Engine engine) {
        return store.add(engine);
    }

    public void delete(int id) {
        store.delete(id);
    }

    public Collection<Engine> findAll() {
        return store.findAll();
    }

    public Engine findById(int id) {
        return store.findById(id);
    }
}
