
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




-- After creating all tables
alter table profile
    add constraint fk_profile_client
    foreign key (client_id) references client (id);

