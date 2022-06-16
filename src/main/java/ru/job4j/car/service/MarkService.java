package ru.job4j.car.service;

import org.springframework.stereotype.Service;
import ru.job4j.car.model.Mark;
import ru.job4j.car.persistence.MarkRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class MarkService {
    private final MarkRepository store;

    public MarkService(MarkRepository store) {
        this.store = store;
    }

    public Optional<Mark> add(Mark mark) {
        return store.add(mark);
    }

    public void delete(int id) {
        store.delete(id);
    }

    public Collection<Mark> findAll() {
        return store.findAll();
    }

    public Mark findById(int id) {
        return store.findById(id);
    }
}
