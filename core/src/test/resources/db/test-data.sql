insert into colors (id, code) values (1, 'Black');
insert into colors (id, code) values (2, 'White');

insert into phones (id, brand, model, price) values ('1', 'test1', 'test1', 1);
insert into phones (id, brand, model, price) values ('2', 'test2', 'test2', 1);
insert into phones (id, brand, model, price) values ('3', 'test3', 'test3', 1);

insert into phone2color (phoneId, colorId) values (1, 1);
insert into phone2color (phoneId, colorId) values (2, 2);
insert into phone2color (phoneId, colorId) values (3, 2);

insert into stocks (phoneId, stock, reserved) values (1, 5, 0);
insert into stocks (phoneId, stock, reserved) values (2, 5, 0);
insert into stocks (phoneId, stock, reserved) values (3, 5, 0);