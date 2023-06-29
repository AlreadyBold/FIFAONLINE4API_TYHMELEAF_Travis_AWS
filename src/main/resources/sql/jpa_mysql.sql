-- 초기 설정 시 테이블 -- 
create table jpabegin.user (
  email varchar(50) not null primary key,
  name varchar(50),
  create_date datetime
) engine innodb character set utf8mb4;

-- 피파 멤버 v0.1 --
  create table jpabegin.member(
  id varchar(50) not null primary key,
  pwd varchar(50),
  nickname varchar(50),
  create_date datetime
) engine innodb character set utf8mb4;

