select sum(amount)
from arrear_record
where equipment_id in (select equipment_id
                       from equipment where user_id = 1001)