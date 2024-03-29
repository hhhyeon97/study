package net.daum.dao;

import java.util.List;

import net.daum.vo.BoardVO;

public interface BoardDAO {

	void insertBoard(BoardVO b); //추상메서드 
	int getTotalCount();
	List<BoardVO> getBoardList(BoardVO b);
	BoardVO getBoardCont(int bno);
	void updateHit(int bno);
	void editBoard(BoardVO eb);
	void delBoard(int bno);
	void updateReplyCnt(int bno, int count); //매개변수명 count로 변경

}
