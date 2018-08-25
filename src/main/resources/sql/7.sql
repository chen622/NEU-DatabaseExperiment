select
  day(time)                                 day,
  (select count(*)
   from serial
   where month(time) = 8 and day(time) = day) amount
from payment_record
  natural join serial
where month(time) = 8
group by day(time)
order by amount desc
limit 1;