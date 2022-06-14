create table if not exists users
(
    id       serial primary key,
    name     varchar(255),
    password varchar(255)
);
create table if not exists bodies
(
    id   serial primary key,
    name varchar(255) not null
);
create table if not exists marks
(
    id   serial primary key,
    name varchar(255) not null
);
create table if not exists engines
(
    id   serial primary key,
    name varchar(255)
);
create table if not exists cars
(
    id        serial primary key,
    name      varchar(255),
    mark_id   int         not null references marks (id),
    body_id   int         not null references bodies (id),
    engine_id int         not null references engines (id),
    year      varchar(50) not null
);
create table if not exists ads
(
    id          serial primary key,
    car_id      int           not null references cars (id),
    description varchar(2000) not null,
    photo       bytea         null,
    sale        boolean,
    user_id     int           not null references users (id),
    created     timestamp
);

drop table ads;
drop table cars cascade;
drop table engines cascade ;
drop table marks cascade ;
drop table bodies cascade ;
drop table users cascade ;

