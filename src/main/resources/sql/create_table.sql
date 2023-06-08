drop table user_info;

-- userinfo table 
CREATE TABLE user_info
( 
    user_no         NUMBER(4) primary key	NOT NULL,
    user_id         VARCHAR2(100) NOT NULL ,
    user_pw         VARCHAR2(100) NOT NULL ,
    user_email      VARCHAR2(100) NOT NULL ,
    user_joindate   DATE default sysdate NOT NULL,
    user_fifaid     VARCHAR2(100) NOT NULL ,
    user_birth      DATE,
    user_delete_yn  NUMBER(2) default '0' NOT NULL,
    user_grade      NUMBER(2)  default '0' NOT NULL
    );
-- userno seq 
CREATE SEQUENCE ADMIN.user_no_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 9999
    NOCYCLE
    NOCACHE
    NOORDER;
-- dummy
insert into user_info (
    user_no,
    user_id,
    user_pw,
    user_email,
    user_fifaid,
    user_grade)
    VALUES (
    user_no_seq.nextval, 
    'admin' , 
    '1234', 
    'aaa@aaa.com' ,
    '앙기분조앙',
    '5'
    );