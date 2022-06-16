create table if not exists users
(
    id          serial primary key,
    name        varchar(255),
    email       varchar(255) unique,
    password    varchar(255),
    phoneNumber varchar(255)
);
create table if not exists bodies
(
    id   serial primary key,
    name varchar(255) unique
);
create table if not exists marks
(
    id   serial primary key,
    name varchar(255) unique
);
create table if not exists engines
(
    id   serial primary key,
    name varchar(255) unique
);
create table if not exists transmissions
(
    id   serial primary key,
    name varchar(255) unique
);
create table if not exists advertisement
(
    id              serial primary key,
    name            varchar(255),
    mileage         int,
    price           int,
    mark_id         int           not null references marks (id),
    body_id         int           not null references bodies (id),
    engine_id       int           not null references engines (id),
    transmission_id int           not null references transmissions (id),
    user_id         int           not null references users (id),
    description     varchar(2000) not null,
--     photo           bytea         null,
    sale            boolean,
    created         timestamp
);
create table if not exists photos
(
    id               serial primary key,
    photo            bytea null
);

drop table engines cascade;
drop table marks cascade;
drop table bodies cascade;
drop table users cascade;
drop table advertisement cascade;
drop table photos cascade;
insert into users(name, email, password, phoneNumber)
values ('Admin', 'Admin', 'Admin', '001');

