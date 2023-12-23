create table IF NOT EXISTS md(
                   id int primary key auto_increment,
                   title text,
                   context longtext,
                   pname varchar(100),
                   save_path varchar(100),
                   blog_url varchar(300),
                   create_time datetime
);

create table IF NOT EXISTS pic(
                   id int primary key auto_increment,
                   pname varchar(100),
                   path varchar(255),
                   create_time datetime
);
create table IF NOT EXISTS setting(
                   id int primary key auto_increment,
                   config_name varchar(100),
                   config_value varchar(100),
                   create_time datetime,
                   remark varchar(200)
);
create table IF NOT EXISTS USER_TEMPLATE(
                   id int primary key auto_increment,
                   header longtext,
                   bottom longtext,
                   create_time datetime
);