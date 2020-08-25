-- Tables

-- Strong Entities

create table users (
  uid char(5),
  uname varchar(20) unique,
  upass varchar(20),
  primary key(uid)
);
create index uidx on users(uid); 

create table customer(
  cid char(5),
  fname varchar(10) not null,
  lname varchar(10) not null,
  balance numeric(10,2) default 0.0,
  primary key(cid),
  check(balance>=0)
);
create index cidx on customer(cid);

create table staff(
  sid char(5), 
  fname varchar(20) not null,
  lname varchar(20) not null,
  street varchar(20) not null,
  city varchar(20) not null,
  state char(2) not null,
  zip char(5) not null,
  salary numeric(10,2) not null default 0.0,
  title varchar(20) not null,
  primary key(sid),
  check(salary>=0)
);
create index sidx on staff(sid);

create table product(
  pid char(5), 
  pname varchar(200) not null,
  size numeric(10,2) not null default 0.0,
  category varchar(10) not null,
  info varchar(500) not null,
  image varchar(40) not null default 'default.png',
  primary key(pid)
);
create index pidx on product(pid);

create table warehouse(
  wid char(5),
  street varchar(50) not null,
  city varchar(20) not null,
  state char(2) not null,
  zip char(5) not null,
  capacity numeric(10,2) not null default 0.0,
  primary key(wid)
);
create index widx on warehouse(wid);

create table supplier(
  ssid char(5),
  fname varchar(10) not null,
  lname varchar(10) not null,
  street varchar(50) not null,
  city varchar(20) not null,
  state char(2) not null,
  zip char(5) not null,
  primary key(ssid)
);
create index ssidx on supplier(ssid);

-- Weak Entities

create table cinfo(
  cid char(5),
  cnumber char(16),
  month char(2) not null,
  year char(4) not null,
  street varchar(50) not null,
  city varchar(20) not null,
  state char(2) not null,
  zip char(5) not null,
  primary key(cid,cnumber),
  foreign key(cid) references customer
);
create index cindx on cinfo(cid, cnumber);

create table pprice(
  pid char(5),
  state char(2) not null,
  price numeric(10,2) not null default 0.0,
  primary key(pid,state),
  foreign key(pid) references product,
  check (price >= 0)
);
create index ppidx on pprice(pid, state);

create table cart(
  ccid char(5),
  cid char(5),
  pid char(5),
  qty int not null default 0,
  primary key(ccid,cid,pid),
  foreign key(pid) references product,
  foreign key(cid) references customer,
  check (qty >= 0)
);
create index ccidx on cart(ccid, cid, pid);

create table orders(
  oid char(6),
  cid char(5),
  pid char(5),
  qty int not null default 0,
  cnumber char(16) not null,
  status varchar(20) not null default 'issued',
  otime TIMESTAMP not null,
  primary key(oid,cid,pid),
  foreign key(cid) references customer,
  foreign key(pid) references product,
  check (qty >= 0)
);
create index oidx on orders(oid, cid, pid);

-- Many-to-Many Relationships

create table stock(
  pid char(5),
  wid char(5),
  qty int not null default 0,
  primary key(pid, wid),
  foreign key(pid) references product,
  foreign key(wid) references warehouse,
  check (qty >= 0)
);
create index wsidx on stock(pid, wid);

create table sells(
  ssid char(5),
  pid char(5),
  price numeric(10,2) not null default 0.0,
  primary key(ssid, pid),
  check (price > 0)
);
create index spidx on sells(ssid, pid);


-- Views

create view cproduct as
  select * 
  from product natural join pprice;
  
create view sorder as
  select "oid", "cid", "pid", "qty", "street", "city", "state", "zip", "status", "otime"
  from orders natural join cinfo;
