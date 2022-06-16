package ru.job4j.car.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.car.model.Photo;

import java.util.List;

@Repository
public class PhotosRepository implements DBStoreSession {
    public final SessionFactory sf;

    public PhotosRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public Photo add(Photo photo) {
        return tx(
                session -> {
                    session.save(photo);
                    return photo;
                }, sf
        );
    }

    public Photo update(Photo photo) {
        return tx(
                session -> {
                    session.update(photo);
                    return photo;
                }, sf
        );
    }

    public boolean delete(int id) {
        return tx(
                session -> {
                    int index = session.createQuery("delete from Photo p where p.advertisement.id = :pId")
                            .setParameter("pId", id)
                            .executeUpdate();
                    return index != 0;
                }, sf
        );
    }

    public Photo findById(int id) {
        return tx(
                session -> {
                    Photo result = (Photo) session.createQuery("from Photo p where p.id = :pId")
                            .setParameter("pId", id)
                            .getSingleResult();
                    return result;
                }, sf
        );
    }

    public List<Photo> findAll() {
        return tx(
                session -> session.createQuery("from Photo ").list(), sf
        );
    }
}
