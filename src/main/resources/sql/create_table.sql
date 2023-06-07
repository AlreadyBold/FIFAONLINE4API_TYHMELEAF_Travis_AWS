CREATE TABLE user_info
( 
    user_no         NUMBER(4)	NOT NULL,
    user_id         VARCHAR2(10),
    user_pw         VARCHAR2(20),
    user_email      VARCHAR2(30),
    user_joindate   DATE,
    user_fifaid     NUMBER(20),
    user_birth      DATE,
    user_delete_yn  NUMBER(2)
    
);