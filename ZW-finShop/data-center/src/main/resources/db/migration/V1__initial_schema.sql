
create table client
(
    id          bigserial   not null    primary key,
    name        varchar(64) not null,
    passport    varchar(64)
);
create unique index idx_client_name on client (name);

create table profile
(
    id          bigserial   not null    primary key,
    name        varchar(32) not null,
    client_id   bigint                  references client (id)
);
create unique index idx_profile_name on profile (name);


create table claim
(
    id          bigserial   not null    primary key,
    client_id   bigint      not null    references client (id),
    status      smallint    not null,
    period      integer,
    amount      integer
);