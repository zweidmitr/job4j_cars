package ru.job4j.car.service;

import org.springframework.stereotype.Service;
import ru.job4j.car.model.Photo;
import ru.job4j.car.persistence.PhotosRepository;

import java.util.Collection;

@Service
public class PhotoService {
    private final PhotosRepository store;

    public PhotoService(PhotosRepository store) {
        this.store = store;
    }

    public Photo add(Photo photo) {
        return store.add(photo);
    }

    public void delete(int id) {
        store.delete(id);
    }

    public Collection<Photo> findAll() {
        return store.findAll();
    }

    public Photo findById(int id) {
        return store.findById(id);
    }
}
