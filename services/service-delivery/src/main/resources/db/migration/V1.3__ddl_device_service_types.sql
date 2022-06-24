create table device_service_types (
    id bigserial primary key,
    device_id uuid not null,
    service_type varchar(10) not null
);