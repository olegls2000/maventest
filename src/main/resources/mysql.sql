insert into country (
    id, code, name, description
) values
(	nextval('country_seq'),
     'EE',
     'Estonia',
     'Estonia ia a greate country'),
(	nextval('country_seq'),
     'FI',
     'Finland',
     'Finland is a cold country'
);
commit;


select *
from country;
select count(*)
from country;
select count(*)
from city;
select count(*)
from city as c
where c.population > 100000;

select *
from city as c
where c.population > 500000;

UPDATE CITY
SET population = 900000
where name = 'Helsinki';

DELETE
from CITY
where name <> 'Tallinn';

select distinct cn.name
--select cn.name as COUNTRY_NAME, sum(ct.population) as POPULATION
from city as ct
         LEFT JOIN country as cn ON ct.country_id = cn.id
--group by cn.name
where ct.name like '%a%'
;

delete
from CITY
where id in (select ct.id
             from city as ct
                      LEFT JOIN country as cn
                                on ct.country_id = cn.id
             where cn.name like 'Est%');


delete
from country
where name = 'Estonia';

select * from country;

