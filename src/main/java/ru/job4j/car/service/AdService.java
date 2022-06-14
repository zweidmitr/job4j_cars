package ru.job4j.car.service;

import ru.job4j.car.model.Ad;
import ru.job4j.car.model.Mark;
import ru.job4j.car.persistence.AdRepository;

import java.util.List;

public class AdService {
    private final AdRepository store;

    public AdService(AdRepository store) {
        this.store = store;
    }

    public List<Ad> findByMarks(Mark mark) {
        return store.findByMarks(mark);
    }

    public List<Ad> findLastDay() {
        return store.findLastDay();
    }

    public List<Ad> findWithPhotos() {
        return store.findWithPhotos();
    }
}
