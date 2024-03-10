create table if not exists users
(
    id                   serial primary key,
    username             varchar(70) not null
);

create table if not exists applications
(
    id                 serial primary key,
    user_id            integer references users (id),
    name               varchar(70) not null,
    phone_number       varchar(70) not null,
    text               text,
    status             varchar(20) not null,
    creation_date      timestamp
);
