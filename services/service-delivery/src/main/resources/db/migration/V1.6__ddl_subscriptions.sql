create table subscriptions (
    subscription_id uuid primary key,
    device_id uuid not null,
    service_type varchar(5) not null,
    subscription_timestamp timestamp not null,
    service_status varchar(10) not null
);
