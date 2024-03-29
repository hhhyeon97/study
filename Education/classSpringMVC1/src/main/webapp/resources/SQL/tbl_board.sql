--0906

--tbl_board 테이블 생성

create table tbl_board(
    bno number(38) primary key --게시판 번호
    ,writer varchar2(50) not null --작성자
    ,title varchar2(200) not null --글제목
    ,content varchar2(4000) not null --글내용
    ,viewcnt number(38) default 0 --조회수 (default 0 제약 조건을 설정하면 레코드를 따로 저장하지 않아도 기본값 0이 저장됨!)
    ,regdate timestamp -- 등록 날짜
);

--table 검색
select * from tbl_board order by bno desc;


-- 댓글 개수를 저장할 replycnt 컬럼을 추가
alter table tbl_board add(replycnt number(38) default 0);

-- 각 게시판 번호에 해당하는 실제 댓글 수를 카운트 해서 새롭게 추가된 replycnt 컬럼 레코드값으로 수정
update tbl_board set replycnt=(select count(rno) from tbl_reply where bno=tbl_board.bno) where bno>0;

commit;

-- bno_seq 시퀀스 생성
create sequence bno_seq
start with 1 --1부터 시작
increment by 1 --1씩 증가
nocache -- 임시 메모리 사용 x
nocycle; -- 시퀀스 최대값 도달시 다시 처음부터 반복 안 함.

--bno_seq 시퀀스 다음 번호값 확인
select bno_seq.nextval from dual;

commit;

--0911

--tbl_reply 댓글 테이블 생성 

create table tbl_reply(
    rno number(38) primary key --댓글번호
    ,bno number(38)  default 0 --게시판번호, 외래키로 추가 설정되어져서 tbl_board테이블의 기본키로 설정된 bno컬럼 레코드값인 
    -- 게시판 번호값만 이 컬럼 레코드 번호값으로 저장된다. 주종 관계이다. 
    ,replyer varchar2(50) not null --댓글 작성자
    ,replytext varchar2(4000) not null -- 댓글내용
    ,regdate timestamp --댓글 등록 날짜
    ,updatedate timestamp --댓글 수정 날짜
);

select * from tbl_reply order by rno desc; -- 댓글 번호를 기준으로 내림차순 정렬

--tbl_reply 테이블의 bno 컬럼에 외래키 추가 설정
alter table tbl_reply add constraint tbl_reply_bno_fk
foreign key(bno) references tbl_board(bno);

-- rno_seq 시퀀스 생성 
create sequence rno_seq
start with 1 --1부터 시작
increment by 1 --1씩 증가
nocache -- 임시 메모리 사용 안 함
nocycle; -- 시퀀스 최대값 번호 생성시 시퀀스 번호값 생성 중지 (다시 1부터 반복 안 함)

-- rno_seq 시퀀스 다음 번호값 확인
select rno_seq.nextval as "시퀀스 다음 번호값" from dual;













