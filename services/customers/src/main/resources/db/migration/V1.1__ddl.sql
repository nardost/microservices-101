-- addresses table
create table addresses (
    id uuid primary key,
    street varchar(255),
    city varchar(20),
    zipCode varchar(5),
    state varchar(2)
);

-- customers table
create table customers (
    id uuid primary key,
    first_name varchar(32) not null,
    last_name varchar(32) not null,
    email_id varchar(320) unique not null,
    phone_number varchar(15) unique not null,
    address_id uuid references addresses (id)
);
