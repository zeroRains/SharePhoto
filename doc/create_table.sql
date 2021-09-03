create table users
(
    id           varchar(100) primary key unique,
    passwd       varchar(50),
    sex          char(1),
    great        int,
    concern      int,
    fan          int,
    username     varchar(50),
    introduction varchar(100),
    url          varchar(50)
);

create table dynamic
(
    id          int auto_increment primary key unique,
    class       varchar(50),
    topic       varchar(50),
    great       int,
    star        int,
    title       varchar(50),
    description text,
    date        varchar(50),
    author      varchar(50),
    foreign key (author) references users (id)
);

create table remark
(
    id      int auto_increment primary key unique,
    date    varchar(50),
    great   int,
    context text,
    author  varchar(50),
    dynamic int,
    foreign key (author) references users (id),
    foreign key (dynamic) references dynamic (id)
);

create table photo
(
    url     varchar(50) primary key,
    dynamic int,
    foreign key (dynamic) references dynamic (id)
);

create table favor
(
    user    varchar(50),
    dynamic varchar(50),
    great   char(1),
    star    char(1),
    primary key (user, dynamic)
);

create table concern
(
    user    varchar(50),
    concern varchar(50),
    primary key (user, concern)
);

create table thumbsup
(
    user   varchar(50),
    remark varchar(50),
    great  char(1),
    primary key (user, remark)
);

