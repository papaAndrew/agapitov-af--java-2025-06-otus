
create table profile
(
    id          bigserial   not null    primary key,
    client_id   bigint      not null    references client (id),
    credential  varchar(32) not null
);
create unique index idx_profile_credential on profile (name);

create table passport
(
    id              bigserial   not null    primary key,
    client_id       bigint      not null    references client (id),
    serial_number   varchar(64) not null
);

create table client
(
    id          bigserial   not null    primary key,
    name        varchar(64) not null
    profile_id  bigint      references profile (id),
    passport_id bigint      references passport (id)
);

