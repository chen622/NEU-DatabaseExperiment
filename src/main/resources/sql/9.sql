select
  *,
  (select count(*)
   from serial
   where serial.bank_id = bank.bank_id and month(time) = 8)
from bank;