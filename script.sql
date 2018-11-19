create database postgres
with owner postgres;

comment on database postgres
is 'default administrative connection database';

create table users
(
  id   serial      not null
    constraint "User_pkey"
    primary key
    constraint user_fkey
    references users,
  name varchar(50) not null
);

alter table users
  owner to postgres;

create table question
(
  id      serial      not null
    constraint question_pkey
    primary key,
  content varchar(50) not null
);

alter table question
  owner to postgres;

create table answer
(
  id      serial      not null
    constraint answer_pkey
    primary key,
  content varchar(50) not null
);

alter table answer
  owner to postgres;

create table relations
(
  id_user     integer not null
    constraint user_fkey
    references users,
  id_question integer
    constraint question_fkey
    references question,
  id_answer   integer
    constraint answer_fkey
    references answer
);

alter table relations
  owner to postgres;

