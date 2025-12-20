
create table client
(
    id          bigserial   not null    primary key,
    name        varchar(64) not null
);

create table profile
(
    id          bigserial   not null    primary key,
    name        varchar(32) not null,
    client_id   bigint                  references client (id)
);
create unique index idx_profile_name on profile (name);

--alter table client
--    add column profile_id  bigint      references profile (id);

create table passport
(
    id              bigserial   not null    primary key,
    client_id       bigint      not null    references client (id),
    serial_number   varchar(64) not null
);

alter table client
    add column passport_id bigint      references passport (id);


-- After creating all tables
alter table profile
    add constraint fk_profile_client
    foreign key (client_id) references client (id);

alter table passport
    add constraint fk_passport_client
    foreign key (client_id) references client (id);

--alter table client
--    add constraint fk_client_profile
--    foreign key (profile_id) references profile (id);

alter table client
    add constraint fk_client_passport
    foreign key (passport_id) references passport (id);