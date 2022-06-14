package ru.job4j.car.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "car_id")
    private Car car;
    private String description;
    private boolean sale;
    private byte[] photo;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date(System.currentTimeMillis());

    public static Ad of(Car car, User user, String description) {
        Ad ad = new Ad();
        ad.car = car;
        ad.user = user;
        ad.description = description;
        return ad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ad ad = (Ad) o;
        return ad.id == id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Post: id= %s, %s, sale= %s, created= %s, photo= %s",
                id, car, sale, created, photo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
