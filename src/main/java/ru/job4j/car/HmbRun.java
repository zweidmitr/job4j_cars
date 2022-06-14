package ru.job4j.car;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.car.model.*;
import ru.job4j.car.persistence.AdRepository;
import ru.job4j.car.service.AdService;

import java.util.List;

public class HmbRun {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

            var store = new AdRepository(sf);
            var service = new AdService(store);
            List<Ad>  result = service.findLastDay();
            result.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
