package ru.job4j.car.service;

import org.springframework.stereotype.Service;
import ru.job4j.car.model.Advertisement;
import ru.job4j.car.persistence.AdRepository;

import java.util.List;

@Service
public class AdService {
    private final AdRepository store;

    public AdService(AdRepository store) {
        this.store = store;
    }

    public void add(Advertisement ad) {
        store.add(ad);
    }

    public void update(Advertisement ad) {
        store.update(ad);
    }

    public void delete(int id) {
        store.delete(id);
    }

    public List<Advertisement> findAll() {
        return store.findAll();
    }

    public List<Advertisement> findAllNew() {
        return store.findAllNew();
    }

    public List<Advertisement> findByMark(int id) {
        return store.findByMark(id);
    }

    public Advertisement findById(int id) {
        return store.findById(id);
    }

    public void setSale(int id) {
        store.setSale(id);
    }
}
