drop database if exists ssafy_mobile_cafe;
select @@global.transaction_isolation, @@transaction_isolation;
set @@transaction_isolation="read-committed";

create database ssafy_mobile_cafe;
use ssafy_mobile_cafe;

create table t_user(
	id varchar(100) primary key,
    name varchar(100) not null,
    pass varchar(100) default "0",
    stamps integer default 0    
);
create table t_product(
	id integer auto_increment primary key,
    name varchar(100) not null,
    type varchar(20) not null,
    price integer not null,
    img varchar(100) not null
);


create  table t_order(
	o_id integer auto_increment primary key,
    user_id varchar(100) not null,
    order_table varchar(20),
    order_time timestamp default CURRENT_TIMESTAMP,    
    completed char(1) default 'N',
    constraint fk_order_user foreign key (user_id) references t_user(id) on delete cascade
);

create table t_order_detail(
	d_id integer auto_increment primary key,
    order_id integer not null,
    product_id integer not null,
    quantity integer not null default 1,
    constraint fk_order_detail_product foreign key (product_id) references t_product(id) on delete cascade,
    constraint fk_order_detail_order foreign key(order_id) references t_order(o_id) on delete cascade
);                                                 

create table t_stamp(
	id integer auto_increment primary key,
    user_id varchar(100) not null,
    order_id integer not null,
    quantity integer not null default 1,
    constraint fk_stamp_user foreign key (user_id) references t_user(id) on delete cascade,
    constraint fk_stamp_order foreign key (order_id) references t_order(o_id) on delete cascade
);

create table t_comment(
	id integer auto_increment primary key,
    user_id varchar(100) not null,
    product_id integer not null,
    rating float not null default 1,
    comment varchar(200),
    constraint fk_comment_user foreign key(user_id) references t_user(id) on delete cascade,
    constraint fk_comment_product foreign key(product_id) references t_product(id) on delete cascade
);

create table t_image(
	id integer auto_increment primary key,
    name varchar(50),
    url varchar(100)
);

create table t_admin(
	id varchar(30) primary key not null,
    pass varchar(30) not null,
    name varchar(30)
);

create table t_table(
	id int primary key auto_increment not null,
    state boolean not null default false
);

create table t_fcm_token(
	user_id varchar(30) primary key not null,
    token varchar(250),
    state varchar(10) default "true"
);
insert into t_table(state) value(false); 
insert into t_table(state) value(false); 
insert into t_table(state) value(false); 
insert into t_table(state) value(false); 
insert into t_table(state) value(false); 
insert into t_table(state) value(false); 
insert into t_table(state) value(false); 
insert into t_table(state) value(false); 
insert into t_table(state) value(false); 
insert into t_table(state) value(false); 

INSERT INTO t_product (name, type, price, img) VALUES ('아메리카노', 'coffee', 4100, 'coffee1.png');
INSERT INTO t_product (name, type, price, img) VALUES ('카페라떼', 'coffee', 4500, 'coffee2.png');
INSERT INTO t_product (name, type, price, img) VALUES ('카라멜 마끼아또', 'coffee', 4800, 'coffee3.png');
INSERT INTO t_product (name, type, price, img) VALUES ('카푸치노', 'coffee', 4800, 'coffee4.png');
INSERT INTO t_product (name, type, price, img) VALUES ('모카라떼', 'coffee', 4800, 'coffee5.png');
INSERT INTO t_product (name, type, price, img) VALUES ('민트라떼', 'coffee', 4300, 'coffee6.png');
INSERT INTO t_product (name, type, price, img) VALUES ('화이트 모카라떼', 'coffee', 4800, 'coffee7.png');
INSERT INTO t_product (name, type, price, img) VALUES ('자몽에이드', 'coffee', 5100, 'coffee8.png');
INSERT INTO t_product (name, type, price, img) VALUES ('레몬에이드', 'coffee', 5100, 'coffee9.png');
INSERT INTO t_product (name, type, price, img) VALUES ('초코칩 쿠키', 'cookie', 1500, 'cookie.png');
commit;


commit;


-- INSERT INTO t_product (name, type, price, img) VALUES  ('아메리카노', 'coffee', 4100, 'coffee1.png'),
--  ('카페라떼', 'coffee', 4500, 'coffee2.png'),
--  ('카라멜 마끼아또', 'coffee', 4800, 'coffee3.png'),
--  ('카푸치노', 'coffee', 4800, 'coffee4.png'),
--  ('모카라떼', 'coffee', 4800, 'coffee5.png'),
--  ('민트라떼', 'coffee', 4300, 'coffee6.png'),
--  ('화이트 모카라떼', 'coffee', 4800, 'coffee7.png'),
--  ('자몽에이드', 'coffee', 5100, 'coffee8.png'),
--  ('레몬에이드', 'coffee', 5100, 'coffee9.png'),
--  ('초코칩 쿠키', 'cookie', 1500, 'cookie.png')

-- INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES  ('ssafy01', 1, 1, '신맛 강한 커피는 좀 별루네요.'),
--  ('ssafy02', 1, 2, '커피 맛을 좀 신경 써야 할 것 같네요.'),
--  ('ssafy03', 1, 3, '그냥 저냥'),
--  ('ssafy04', 4, 4, '갠춘한 맛'),
--  ('ssafy05', 5, 5, 'SoSSo'),
--  ('ssafy06', 6, 6, '그냥 저냥 먹을만함'),
--  ('ssafy07', 7, 10, '이집 화이트 모카라떼가 젤 나은듯'),
--  ('ssafy08', 8, 8, '자몽 특유의 맛이 살아있네요.'),
--  ('ssafy09', 8, 9, '수제 자몽에이드라 그런지 맛나요.'),
--  ('ssafy10', 10, 10, '초코칩 쿠키 먹으로 여기 옵니다.')

-- select * from t_user;
-- select * from t_product;
-- select * from t_order, t_order_detail where t_order.o_id = t_order_detail.order_id;
-- select o_id from t_order order by o_id desc limit 1;
-- select * from t_order_detail;
-- select * from t_order;
-- select * from t_image;
-- select * from t_table;
-- select * from t_fcm_token;



-- select * from t_order, t_order_detail, t_product where o_id = order_id and product_id = id and order_time between date_format(now(), '2022-05-01') and now() order by o_id;
-- select sum(price*quantity) from t_order, t_order_detail, t_product where o_id = order_id and product_id = id and order_time between date_format(now(), '2022-05-01') and now() order by o_id;
 -- 월별 일간매출
-- select DATE(`order_time`) as orderdate, sum(price*quantity) as total from t_order, t_order_detail, t_product where o_id = order_id and product_id = id and MONTH(`order_time`) = 5 group by DATE(`order_time`) order by order_time;
 -- 월별 일간/타입
-- select DATE(`order_time`) as orderdate, sum(price*quantity) as total, type from t_order, t_order_detail, t_product where o_id = order_id and product_id = id and MONTH(`order_time`) = 5 group by DATE(`order_time`), type order by order_time;

 -- 연간 월별매출
-- select YEAR(`order_time`) as year, MONTH(`order_time`) as orderdate, sum(price*quantity) as total from t_order, t_order_detail, t_product where o_id = order_id and product_id = id and YEAR(`order_time`) = 2022 group by MONTH(`order_time`) order by order_time;
-- 연간 월별매출/타입
-- select MONTH(`order_time`) as orderdate, sum(price*quantity) as total, type as total from t_order, t_order_detail, t_product where o_id = order_id and product_id = id and YEAR(`order_time`) = 2022 group by MONTH(`order_time`), type order by order_time;

-- 일간 시간매출
-- select YEAR(`order_time`) as year, MONTH(`order_time`) as month, DAY(`order_time`) as day, HOUR(`order_time`) as hour, sum(price*quantity) as total from t_order, t_order_detail, t_product where o_id = order_id and product_id = id and MONTH(`order_time`) = 5 and DAY(`order_time`) = 19 group by HOUR(`order_time`) order by order_time;
-- 일간 시간매출 / 타입
-- select HOUR(`order_time`) as orderdate , sum(price*quantity) as total, type from t_order, t_order_detail, t_product where o_id = order_id and product_id = id and MONTH(`order_time`) = 5 and DAY(`order_time`) = 19 group by HOUR(`order_time`), type order by order_time;
