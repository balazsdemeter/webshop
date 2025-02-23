create sequence shop_user_seq start with 1 increment by 50;
create table shop_user (id integer not null, facebook_id varchar(255), password varchar(255), username varchar(255), email varchar(255), primary key (id));
create table shop_user_roles (shop_user_id integer not null, roles varchar(255));
alter table if exists shop_user_roles add constraint FK52dcdmt8c28ls6tsubcvlpvi foreign key (shop_user_id) references shop_user;