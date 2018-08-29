select
  *,
  (select count(*)
   from serial join payment_record on serial.serial_id = payment_record.serial_number
   where serial.bank_id = bank.bank_id and month(time) = 8 and is_edit = 0)
from bank;