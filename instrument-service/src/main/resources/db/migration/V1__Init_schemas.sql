create schema ducks;
create schema beans;

create table ducks.instrument(
    id UUID primary key,
    name varchar(255) not null,
    type varchar(255)
);

create table beans.instrument(
    id UUID primary key,
    name varchar(255) not null,
    type varchar(255)
);