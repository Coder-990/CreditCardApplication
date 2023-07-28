DROP DATABASE IF EXISTS RBA;
CREATE DATABASE RBA;
use RBA;

drop table if exists PERSON;
drop table if exists FILE;

create table PERSON
(
    id        bigint not null auto_increment,
    OIB       varchar(255),
    NAME      varchar(255),
    LAST_NAME varchar(255),
    FILE      bigint,
    primary key (id)
);

create table FILE
(
    id     bigint not null auto_increment,
    STATUS varchar(255),
    primary key (id)
);

alter table PERSON
    add constraint UK_4dexjyuf37ycrj612bfc8fl9c unique (FILE);
alter table PERSON
    add constraint FKbumc7ja3cfw4a8ukpdvix9x2t foreign key (FILE) references FILE (id);