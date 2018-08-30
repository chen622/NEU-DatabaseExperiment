# update arrear_record
#   natural join equipment
# set amount =
# if(year(edit_time) = year(curdate()),
#    amount + datediff(now(), edit_time) * principal * if(category = "01", cast((select val
#                                                                                from code
#                                                                                where name = "normal_same") as decimal(
#                                                                               10, 6)), cast((select val
#                                                                                              from code
#                                                                                              where name =
#                                                                                                    "normal_diff") as
#                                                                                             decimal(10, 6))),
#    amount + datediff(now(), edit_time) * principal * if(category = "01", cast((select val
#                                                                                from code
#                                                                                where name = "company_same") as
#                                                                               decimal(10, 6)), cast((select val
#                                                                                                     from code
#                                                                                                     where name =
#                                                                                                           "company_diff")
#                                                                                                    as decimal(10, 6)))),
#    edit_time = now()
#    where date(edit_time) != curdate() and datediff(now(), create_time) > 30 and amount !=0;
#

select *
from (select sum(arrear_record.amount) 应缴金额
      from arrear_record
      where arrear_record.amount > 0) inmoney natural join (select ifnull(sum(serial.amount), 0) 实缴金额
                                                            from serial
                                                              join payment_record record on serial.serial_id = record.serial_number
                                                            where
                                                              date(serial.time) = date(now()) and is_edit = 0) outmoney;
