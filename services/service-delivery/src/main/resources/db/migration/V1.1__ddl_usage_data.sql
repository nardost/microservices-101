create table usage_data (
    id bigserial primary key,
    device_id uuid not null,
    capture_timestamp date not null,
    usage_data_amount decimal(10, 3) not null
);
