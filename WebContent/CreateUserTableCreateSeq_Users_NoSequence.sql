--2022.01.11(화)13:30
--users 테이블 삭제
drop TABLE users;

--users 시퀀스 삭제 seq_users_no
drop SEQUENCE seq_users_no;

--users 테이블 생성
create table users (
    no number(5), 
    id varchar2(20) unique not null,
    password varchar(20) not null,
    name varchar2(20),
    gender varchar2(10),
    primary key(no)
);

--users 시퀀스 생성
create sequence seq_users_no 
increment by 1
start with 1
nocache;

--insert문 2개 넣기
insert into users
values(seq_users_no.nextval, 'aaa', '1234', '정우성', 'male');

insert into users
values(seq_users_no.nextval, 'bbb', '1234', '이효리', 'female');


--commit
commit;


--select문
select *
from users;