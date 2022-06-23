-- Subscriptions table
create table subscriptions (
    id uuid primary key,
    subscriber_id uuid not null,
    plan_id uuid not null,
    subscription_timestamp date not null,
    status varchar(10) not null
);
