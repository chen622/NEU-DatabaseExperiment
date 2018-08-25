select *
from user
where user_id in (select user_id
                  from equipment
                  where equipment_id in (
                    select equipment_id
                    from arrear_record
                    where amount > 0))