package ru.job4j.car.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.car.model.Engine;

import java.util.List;
import java.util.Optional;

@Repository
public class EngineRepository implements DBStoreSession {
    private final SessionFactory sf;

    public EngineRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public Optional<Engine> add(Engine engine) {
        try {
            tx(session -> session.save(engine), sf);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(engine);
    }

    public boolean delete(int id) {
        return tx(
                session -> {
                    int index = session.createQuery("delete from Engine e where e.id = :eId")
                            .setParameter("eId", id)
                            .executeUpdate();
                    return index != 0;
                }, sf
        );
    }

    public Engine findById(int id) {
        return tx(session -> session.get(Engine.class, id), sf);
    }

    public List<Engine> findAll() {
        return tx(session -> session.createQuery("from Engine").list(), sf);
    }
}
