select category c,(select count(*) from equipment where category = c) amount
from equipment
group by category
order by amount desc