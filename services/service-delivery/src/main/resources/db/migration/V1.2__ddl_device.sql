create table devices (
    device_id uuid primary key,
    device_state varchar(10) not null,
    end_of_life date not null
);
