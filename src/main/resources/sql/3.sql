update arrear_record
  natural join equipment
set amount                                                                                         =
if(year(edit_time) = year(curdate()),
   amount + datediff(now(), edit_time) * principal * if(category = "01", 0.001, 0.002),
   amount + datediff(now(), edit_time) * principal * if(category = "01", 0.001, 0.003)), edit_time = now()
where date(edit_time) != curdate() and datediff(now(), edit_time) > 30;


select *
from (select sum(arrear_record.amount) 应缴金额
      from arrear_record
      where arrear_record.amount > 0) inmoney natural join (select ifnull(sum(serial.amount),0) 实缴金额
                                                            from serial
                                                              natural join payment_record
                                                            where
                                                              date(serial.time) = "2018-8-22" and is_edit = 0) outmoney;
