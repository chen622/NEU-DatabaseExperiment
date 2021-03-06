select
  *,
  (select sum(degree - last_degree)
   from check_record
   where year(time) = year(now()) and month(time) = 8 and equipment_id in (select equipment_id
                                              from equipment
                                              where user_id = u.user_id)) all_use
from user u
order by all_use desc
limit 3
