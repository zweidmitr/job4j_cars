package ru.job4j.car.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.car.model.Transmission;

import java.util.List;
import java.util.Optional;

@Repository
public class TransmissionRepository implements DBStoreSession {
    private final SessionFactory sf;

    public TransmissionRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public Optional<Transmission> add(Transmission transmission) {
        try {
            tx(session -> session.save(transmission), sf);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(transmission);
    }

    public boolean delete(int id) {
        return tx(
                session -> {
                    int index = session.createQuery("delete from Transmission t where t.id = :eId")
                            .setParameter("eId", id)
                            .executeUpdate();
                    return index != 0;
                }, sf
        );
    }

    public Transmission findById(int id) {
        return tx(session -> session.get(Transmission.class, id), sf);
    }

    public List<Transmission> findAll() {
        return tx(session -> session.createQuery("from Transmission").list(), sf);
    }
}
