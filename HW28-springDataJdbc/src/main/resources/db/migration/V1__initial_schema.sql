create table client
(
    id          bigserial   not null    primary key,
    name        varchar(50) not null,
    address_id  bigint
);

create table address
(
    client_id   bigint      not null    references client (id),
    street varchar(64)      not null
);
create index idx_address_client_id on address (client_id);

create table phone
(
    id          bigserial   not null    primary key,
    client_id   bigint      not null    references client (id),
    number      varchar(64) not null
);
create index idx_phone_client_id on phone (client_id);