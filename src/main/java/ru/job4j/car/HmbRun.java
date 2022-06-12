package ru.job4j.car;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.car.model.*;

public class HmbRun {
    public static void main(String[] args) {
        Post result;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            result = session.createQuery(
                            "select distinct pt from Post pt "
                                    + "join fetch pt.car c "
                                    + "join fetch  c.body "
                                    + "join fetch  c.engine "
                                    + "join fetch  c.mark "
                                    + "where pt.id = :pId", Post.class)
                    .setParameter("pId", 1).uniqueResult();
            System.out.println(result);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private static void save(Session session) {
        User user = User.of("Admin", "Password");
        session.save(user);
        Engine engine = Engine.of("frenzy");
        session.save(engine);
        Mark mark = Mark.of("future");
        session.save(mark);
        Body body = Body.of("superTop");
        session.save(body);
        Car car = Car.of("muscle car", engine, mark, body, "1975");
        session.save(car);
        Post post = Post.of(car, user, "testPost");
        session.save(post);
    }
}
