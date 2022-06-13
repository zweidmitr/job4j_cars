package ru.job4j.car;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.car.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AdRepository {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            showModels(session);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }

    }

    private static void showModels(Session session) {
        List<Post> result = session.createQuery(
                        "select distinct pt from Post pt "
                                + "join fetch pt.car c "
                                + "join fetch c.mark m "
                                + "where m.id = :mId", Post.class)
                .setParameter("mId", 2)
                .list();
        result.forEach(System.out::println);
    }

    private static void showWithPhotos(Session session) {
        List<Post> result = session.createQuery(
                        "select  pt from Post pt "
                                + "where pt.photo != null", Post.class)
                .list();
        result.forEach(System.out::println);
    }

    private static void showLastDay(Session session) {
        List<Post> result = session.createQuery(
                        "select pt from Post pt "
                                + "where pt.created >= current_date", Post.class)
                .list();
        result.forEach(System.out::println);
    }

    private static void save(Session session) {
        User user = User.of("Adminos", "Pass");
        User userTwo = User.of("User", "User");
        session.save(user);
        session.save(userTwo);
        Engine engine = Engine.of("frenzy");
        Engine engineTwo = Engine.of("slowly");
        session.save(engine);
        session.save(engineTwo);
        Mark mark = Mark.of("futu");
        session.save(mark);
        Body body = Body.of("tipTop");
        Body bodyTwo = Body.of("topTop");
        session.save(body);
        session.save(bodyTwo);
        Car car = Car.of("super car", engine, mark, body, "1999");
        Car carTwo = Car.of("slow car", engineTwo, mark, bodyTwo, "1999");
        session.save(car);
        session.save(carTwo);
        Post post = Post.of(car, user, "testPost millennium");
        Post postTwo = Post.of(carTwo, userTwo, "nooooooo");
        session.save(post);
        session.save(postTwo);
    }
}
