--The plans record
create table plans (
    id uuid primary key,
    plan_name varchar(20) unique not null,
    base_units decimal not null,
    max_units decimal not null,
    rate_per_unit decimal not null
);
