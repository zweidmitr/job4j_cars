package ru.job4j.car.persistence;

import org.hibernate.SessionFactory;
import ru.job4j.car.model.*;

import java.util.List;

public class AdRepository implements DBStoreSession {
    private final SessionFactory sf;

    public AdRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Ad> findAll() {
        return tx(
                session -> session.createQuery("select distinct a from Ad a").list(), sf);
    }

    public List<Ad> findWithPhotos() {
        return tx(session -> session.createQuery(
                "select a from Ad a "
                        + "where  a.photo != null", Ad.class
        ).list(), sf);
    }

    public List<Ad> findByMarks(Mark mark) {
        return tx(session -> session.createQuery(
                        "select distinct a from Ad a "
                                + "join fetch a.car c "
                                + "join fetch c.mark m "
                                + "where m.id = :mId", Ad.class)
                .setParameter("mId", mark.getId())
                .list(), sf);
    }

    public List<Ad> findLastDay() {
        return tx(session -> session.createQuery(
                        "select a from Ad  a "
                                + "where a.created >= current_date", Ad.class)
                .list(), sf);
    }
}
