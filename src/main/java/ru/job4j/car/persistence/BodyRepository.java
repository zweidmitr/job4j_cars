package ru.job4j.car.persistence;

import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.car.model.Body;


import java.util.List;
import java.util.Optional;

@Repository
public class BodyRepository implements DBStoreSession {
    private final SessionFactory sf;

    public BodyRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public Optional<Body> add(Body body) {
        try {
            tx(session -> session.save(body), sf);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(body);
    }

    public boolean delete(int id) {
        return tx(
                session -> {
                    int index = session.createQuery("delete from Body b where b.id = :bId")
                            .setParameter("bId", id)
                            .executeUpdate();
                    return index != 0;
                }, sf
        );
    }

    public Body findById(int id) {
        return tx(
                session -> {
                    return (Body) session.createQuery("from Body b "
                                    + "where b.id= :bId")
                            .setParameter("bId", id)
                            .uniqueResult();
                }, sf
        );

    }

    public List<Body> findAll() {
        return tx(session -> session.createQuery("from Body").list(), sf);
    }
}
