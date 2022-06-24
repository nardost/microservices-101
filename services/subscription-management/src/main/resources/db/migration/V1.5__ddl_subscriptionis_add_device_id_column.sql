-- add device_id column to subscriptions table
alter table if exists subscriptions add column if not exists device_id uuid;