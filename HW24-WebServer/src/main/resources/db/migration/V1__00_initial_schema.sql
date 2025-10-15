-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence client_SEQ start with 1 increment by 1;
create sequence address_SEQ start with 1 increment by 1;
create sequence phone_SEQ start with 1 increment by 1;
create sequence acc_SEQ start with 1 increment by 1;

create table client
(
    id   bigint not null primary key,
    name varchar(50),
    address_id bigint,
    user_id bigint
);

create table address
(
    id bigint not null primary key,
    client_id bigint,
    street varchar(64)
);
create table phone
(
    id bigint not null primary key,
    client_id bigint,
    number varchar(64)
);
create table acc
(
    id bigint not null primary key,
    client_id bigint,
    passw varchar(64)
);
