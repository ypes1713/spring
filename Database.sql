SELECT * FROM spring_boot_eshop.user;
insert into user (login, password, enabled) values ('ming', '{noop}001811', true );
insert into user (login, password, enabled) values ('v', '{noop}0521', true );

--/*建立使用者的資料，包含名稱及密碼

SELECT * FROM spring_boot_eshop.role;
insert into role (login, role) values ('ming', 'ROLE_EMPLOYEE');
insert into role (login, role) values ('v', 'ROLE_CLIENT');

--/*決定使用者的權限

