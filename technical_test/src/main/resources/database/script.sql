create table person
(
    id                int identity
        primary key,
    date_of_birth     date,
    firts_name        varchar(255),
    last_name         varchar(255),
    permanent_address int
        constraint FK7tuppr9cqib1yyo3gyafq6uuh
            references address,
    temporary_address int
        constraint FK44ur5j4s3sc5l06ai376n5dnu
            references address
)
go

create unique index UKi4dgqrxojj88cf6txslb3dexi
    on person (permanent_address)
    where [permanent_address] IS NOT NULL
go

create unique index UKp0ukh00i9e1r26swvqbldh6nv
    on person (temporary_address)
    where [temporary_address] IS NOT NULL
go


