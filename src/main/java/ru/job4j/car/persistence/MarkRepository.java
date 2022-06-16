package ru.job4j.car.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.car.model.Mark;

import java.util.List;
import java.util.Optional;

@Repository
public class MarkRepository implements DBStoreSession {
    private final SessionFactory sf;

    public MarkRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public Optional<Mark> add(Mark mark) {
        try {
            tx(session -> session.save(mark), sf);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(mark);
    }

    public Mark findById(int id) {
        return tx(session -> session.get(Mark.class, id), sf);
    }

    public List<Mark> findAll() {
        return tx(session -> session.createQuery("from Mark").list(), sf);
    }

    public boolean delete(int id) {
        return tx(session -> {
                    int index = session.createQuery("delete from Mark m where m.id = :mId")
                            .setParameter("mId", id)
                            .executeUpdate();
                    return index != 0;
                }, sf
        );
    }
}
