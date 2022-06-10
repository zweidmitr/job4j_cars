package ru.job4j.car;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.car.model.Car;
import ru.job4j.car.model.Driver;
import ru.job4j.car.model.Engine;

public class HmbRun {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            var frenzy = Engine.of("frenzy");
            session.save(frenzy);

            var mitra = Driver.of("Mitra");
            var xenia = Driver.of("Xenia");
            var varlev = Driver.of("Varvara");
            session.save(mitra);
            session.save(xenia);
            session.save(varlev);

            Car one = Car.of("history", frenzy);
            one.getDrivers().add(mitra);
            one.getDrivers().add(xenia);
            one.getDrivers().add(varlev);

            Car two = Car.of("now", frenzy);
            two.getDrivers().add(mitra);

            session.persist(one);
            session.persist(two);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
