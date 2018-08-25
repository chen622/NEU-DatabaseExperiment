select *
from user
where exists(select *
             from equipment
             where 1000 = equipment.user_id and datediff(now(), (select min(time)
                                                                 from check_record
                                                                 where check_record.equipment_id =
                                                                       equipment.equipment_id)
                                                ) < 182);