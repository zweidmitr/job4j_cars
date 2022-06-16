package ru.job4j.car.service;

import org.springframework.stereotype.Service;
import ru.job4j.car.model.Transmission;
import ru.job4j.car.persistence.TransmissionRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class TransmissionService {
    private final TransmissionRepository store;

    public TransmissionService(TransmissionRepository store) {
        this.store = store;
    }

    public Optional<Transmission> add(Transmission transmission) {
        return store.add(transmission);
    }

    public void delete(int id) {
        store.delete(id);
    }

    public Collection<Transmission> findAll() {
        return store.findAll();
    }

    public Transmission findById(int id) {
        return store.findById(id);
    }
}
