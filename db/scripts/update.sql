create table if not exists engines
(
    id   serial primary key,
    name varchar(255)
);
create table if not exists drivers
(
    id   serial primary key,
    name varchar(255)
);
create table if not exists cars
(
    id        serial primary key,
    name      varchar(255),
    engine_id int not null unique references engines (id)
);
create table if not exists history_owner
(
    id        serial primary key,
    driver_id int not null references drivers (id),
    car_id    int not null references cars (id)

);

drop table drivers cascade ;
drop table cars cascade ;
drop table engines cascade ;
drop table history_owner cascade ;