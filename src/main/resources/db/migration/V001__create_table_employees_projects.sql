-- create table employee
-- (
--     id     integer not null auto_increment,
--     active bit     not null,
--     name   varchar(255),
--     primary key (id)
-- ) engine=InnoDB
-- create table employee_projects
-- (
--     employee_id integer not null,
--     projects_id integer not null
-- ) engine=InnoDB
-- create table project
-- (
--     id          integer not null auto_increment,
--     description varchar(255),
--     in_progress bit     not null,
--     name        varchar(255),
--     primary key (id)
-- ) engine=InnoDB
--
-- alter table employee_projects drop index UK_4jypfcavfedhsivky8co9wvqa;
-- alter table employee_projects
--     add constraint UK_4jypfcavfedhsivky8co9wvqa unique (projects_id);
-- alter table employee_projects
--     add constraint FKg10a7uho2lylw8g080l5j4gyk foreign key (projects_id) references project (id);
-- alter table employee_projects
--     add constraint FK97jl81fsrbblkqfoqwg2o7yps foreign key (employee_id) references employee (id)