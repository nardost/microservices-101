alter table subscriptions alter column plan_id type varchar(5);
alter table subscriptions rename plan_id to subscription_type;