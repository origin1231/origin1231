create table tbl_member(
	userid varchar(50) not null,
    userpw varchar(50) not null,
    username varchar(50) not null,
    email varchar(100),
    regdate timestamp default now(),
    updatedate timestamp default now(),
    primary key(userid)
);

select *
from tbl_member;

create table tbl_board(
	bno int not null auto_increment,
    title varchar(200) not null,
    content text null,
    writer varchar(50) not null,
    regdate timestamp not null default now(),
    viewcnt int default 0,
    primary key(bno)
);

-- 새로운 게시물 등록
insert into tbl_board(title,content,writer)
values('제목입니다','내용입니다','user00');

-- 게시물 조회 사용
select *
from tbl_board
where bno=1;

-- 게시물 전체목록에 사용하는 sql
select *
from tbl_board
where bno > 0
order by bno desc;

-- 게시물 수정에 사용하는 sql
update tbl_board 
set title ='수정된 제목'
where bno = '1';

-- 게시물 삭제
delete from tbl_board
where bno = 1;

-- 댓글 관련 테이블 생성
create table tbl_reply (
	rno int not null auto_increment,
    bno int not null default 0,
    replytext varchar(1000) not null,
    replyer varchar(50) not null,
    regdate timestamp not null default now(),
    updatedate timestamp not null default now(),
    primary key(rno)
);

alter table tbl_reply add constraint fk_board
foreign key (bno) references tbl_board (bno);

commit;

-- aop 관련 
create table tbl_user(
	uid varchar(50) not null,
    upw varchar(50) not null,
    uname varchar(50) not null,
    upoint int not null default 0,
    primary key(uid)
);

create table tbl_message(
	mid int not null auto_increment,
    targetid varchar(50) not null,
    sender varchar(50) not null,
    message text not null,
    opendate timestamp,
    senddate timestamp not null default now(),
    primary key(mid)
);

alter table tbl_message add constraint fk_usertarget
foreign key(targetid) references tbl_user (uid);

alter table tbl_message add constraint fk_usersender
foreign key(targetid) references tbl_user (uid);

insert into tbl_user(uid, upw, uname) values ('user00','user00','아이언맨');
insert into tbl_user(uid, upw, uname) values ('user01','user01','캡틴아메리카');
insert into tbl_user(uid, upw, uname) values ('user02','user02','토르');
insert into tbl_user(uid, upw, uname) values ('user03','user03','타노스');

commit;

-- 카운트 컬럼 추가
alter table tbl_board add column replycnt int default 0;

-- 파일첨부테이블 추가
create table tbl_attach(
	fullName varchar(150), 
    bno int not null,
    regdate timestamp default now(),
    primary key(fullName)
);

alter table tbl_attach add constraint fk_board_attach
foreign key (bno) references tbl_board (bno);

select *
from tbl_attach;


select *
from tbl_message
;

select *
from tbl_user;