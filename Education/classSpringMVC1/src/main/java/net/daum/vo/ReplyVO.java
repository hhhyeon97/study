package net.daum.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data //setter(), getter(), toString() 등 메서드 자동 제공
public class ReplyVO { //tbl_reply 테이블의 컬럼명과 일치하는 변수명을 가진 데이터 저장빈 클래스
	
	private int rno; //댓글번호
	private int bno; //게시판번호
	private String replyer; //댓글작성자
	private String replytext; //댓글내용
	private Timestamp regdate; //댓글 등록날짜
	private Timestamp updatedate; //댓글 수정날짜
	
	
	

}
