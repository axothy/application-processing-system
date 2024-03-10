create table if not exists users
(
    id                   serial primary key,
    username             varchar(70) not null,
    password             varchar(70) not null,
    role                 varchar(20) not null
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


insert into users (username, password, role) values ('user1', '$2a$12$i2kmzg5iiDDIsHRuFCTKXegz3Bf6z3te2MxtNUfjAdMBHr26/8Ox6', 'ROLE_USER');
insert into users (username, password, role) values ('user2', '$2a$12$i2kmzg5iiDDIsHRuFCTKXegz3Bf6z3te2MxtNUfjAdMBHr26/8Ox6', 'ROLE_USER');
insert into users (username, password, role) values ('user3', '$2a$12$i2kmzg5iiDDIsHRuFCTKXegz3Bf6z3te2MxtNUfjAdMBHr26/8Ox6', 'ROLE_USER');

insert into users (username, password, role) values ('operator1', '$2a$12$i2kmzg5iiDDIsHRuFCTKXegz3Bf6z3te2MxtNUfjAdMBHr26/8Ox6', 'ROLE_OPERATOR');
insert into users (username, password, role) values ('operator2', '$2a$12$i2kmzg5iiDDIsHRuFCTKXegz3Bf6z3te2MxtNUfjAdMBHr26/8Ox6', 'ROLE_OPERATOR');
insert into users (username, password, role) values ('operator3', '$2a$12$i2kmzg5iiDDIsHRuFCTKXegz3Bf6z3te2MxtNUfjAdMBHr26/8Ox6', 'ROLE_OPERATOR');

insert into users (username, password, role) values ('admin1', '$2a$12$i2kmzg5iiDDIsHRuFCTKXegz3Bf6z3te2MxtNUfjAdMBHr26/8Ox6', 'ROLE_ADMIN');

