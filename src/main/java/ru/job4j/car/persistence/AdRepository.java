package ru.job4j.car.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.car.model.Advertisement;

import java.util.List;

@Repository
public class AdRepository implements DBStoreSession {
    private final SessionFactory sf;

    public AdRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public Advertisement add(Advertisement ad) {
        return tx(
                session -> {
                    session.save(ad);
                    return ad;
                }, sf
        );
    }

    public Advertisement update(Advertisement ad) {
        return tx(
                session -> {
                    session.update(ad);
                    return ad;
                }, sf
        );
    }

    public List<Advertisement> findAll() {
        return tx(session -> session.createQuery("select distinct ad from Advertisement ad "
                + "join fetch ad.photos").list(), sf);

    }

    public List<Advertisement> findAllNew() {
        return tx(session -> session.createQuery("select distinct ad from Advertisement ad "
                        + "join fetch ad.photos where ad.sale = :sale")
                .setParameter("sale", false)
                .list(), sf);
    }

    public List<Advertisement> findByMark(int id) {
        return tx(session -> session.createQuery("select distinct ad from Advertisement ad "
                        + "join fetch ad.photos where ad.mark.id = :mId")
                .setParameter("mId", id)
                .list(), sf);
    }

    public Advertisement findById(int id) {
        return tx(session -> {
            return (Advertisement) session.createQuery(
                            "select distinct ad from Advertisement ad "
                                    + "join fetch ad.photos where  ad.id = :adId")
                    .setParameter("adId", id)
                    .getSingleResult();
        }, sf);
    }

    public void delete(int id) {
        tx(session -> {
            int index = session.createQuery("delete from Advertisement ad where ad.id = :adId")
                    .setParameter("adId", id)
                    .executeUpdate();
            return index != 0;
        }, sf);
    }

    public void setSale(int id) {
        tx(
                session -> {
                    int index = session.createQuery("update Advertisement ad set "
                                    + "ad.sale = :sale where ad.id = : adId")
                            .setParameter("sale", true)
                            .setParameter("adId", id)
                            .executeUpdate();
                    return index != 0;
                }, sf
        );
    }
}
