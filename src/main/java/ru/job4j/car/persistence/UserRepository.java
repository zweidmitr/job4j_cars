package ru.job4j.car.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.car.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements DBStoreSession {
    private final SessionFactory sf;

    public UserRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public Optional<User> add(User user) {
        try {
            tx(session -> session.save(user), sf);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(user);
    }

    public boolean delete(int id) {
        return tx(
                session -> {
                    int index = session.createQuery("delete from User u where u.id = :uId")
                            .setParameter("uId", id)
                            .executeUpdate();
                    return index != 0;
                }, sf
        );
    }

    public List<User> findAll() {
        return tx(session -> session.createQuery("from User ").list(), sf);
    }

    public Optional<User> findByEmail(String email, String password) {
        return tx(
                session -> {
                    Optional<User> optUser = Optional.empty();
                    Query query = session.createQuery("from User u "
                                    + "where u.email= :uEmail and  u.password= :uPassword")
                            .setParameter("uEmail", email)
                            .setParameter("uPassword", password);
                    try {
                        optUser = query.uniqueResultOptional();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return optUser;
                }, sf
        );
    }
}
