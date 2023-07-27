DROP DATABASE IF EXISTS RBA;
CREATE DATABASE RBA;
use RBA;

create table PERSON
(
    id                 bigint not null auto_increment,
    OIB                varchar(255),
    NAME               varchar(255),
    LAST_NAME          varchar(255),
    CREDIT_CARD_STATUS varchar(255),
    primary key (id)
);