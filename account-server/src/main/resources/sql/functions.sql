
create or replace function get_accounts_with_roles_count()
    returns Table
            (
                today_count  bigint,
                month_count  bigint,
                year_count   bigint,
                user_count   bigint,
                doctor_count bigint,
                admin_count  bigint,
                total_count  bigint
            )
as
$$
begin
    return query
select (select count(*)
        from accounts
        where accounts.created_at >= 'yesterday'::timestamp)            as today_count,
       (select count(*)
        from accounts
        where accounts.created_at >= date_trunc('MONTH', current_date)) as month_count,
       (select count(*)
        from accounts
        where accounts.created_at >= date_trunc('YEAR', current_date))  as year_count,
       (select count(*)
        from roles
                 inner join public.account_roles ar on roles.role_id = ar.role_id
        where roles.role = 'ADMIN')                                     as admin_count,
       (select count(*)
        from roles
                 inner join public.account_roles ar on roles.role_id = ar.role_id
        where roles.role = 'DOCTOR')                                    as doctor_count,
       (select count(*)
        from roles
                 inner join public.account_roles ar on roles.role_id = ar.role_id
        where roles.role = 'USER')                                      as user_count,
       count(acc)                                                          total_account
from accounts acc;
end;
$$ language plpgsql;