select *
from user where user_id in (select user_id from equipment group by equipment.user_id having count(equipment_id)>2);