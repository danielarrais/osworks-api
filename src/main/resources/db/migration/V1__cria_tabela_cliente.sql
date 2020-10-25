create table cliente
(
    id       serial not null,
    nome     varchar(255),
    email    varchar(255),
    telefone varchar(20),
    primary key (id)
)