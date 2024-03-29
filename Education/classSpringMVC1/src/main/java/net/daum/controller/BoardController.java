package net.daum.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.daum.service.BoardService;
import net.daum.vo.BoardVO;

//인터넷 웹에서 인식하는 곳 !

@Controller //@Controller 애노테이션을 추가함으로써 웹에서 인식되는 스프링 컨트롤러가 된다.
@RequestMapping("/board/*") //경로를 구분하기 위해서 컨트롤러 자체 매핑주소 /board/* 등록
public class BoardController { //스프링 MVC 게시판 컨트롤러 

	@Autowired //자동 의존성 주입
	private BoardService boardService;
	
	//게시판 글쓰기 폼
	@RequestMapping(value="/board_write",method=RequestMethod.GET) //get으로 접근하는 매핑주소 처리,
	//board_write 매핑주소 등록
	public void board_write(Model wm, HttpServletRequest request) {
		//리턴 타입이 없는 void형이면 매핑주소가 뷰페이지 파일명이 된다. 뷰리졸브 경로는 /WEB-INF/views/board/board_write.jsp
		
		int page=1;
		if(request.getParameter("page")!=null) {// get으로 전달된 쪽번호가 있는 경우
			page=Integer.parseInt(request.getParameter("page"));
			//쪽번호인 페이지 번호를 정수 숫자로 변경해서 저장
		}
		wm.addAttribute("page",page); // 페이징에서 내가 본 쪽번호로 이동하기 위한 책갈피 기능을 구현하기 위해서
		// page 키이름에 쪽번호를 저장해서 전달한다.
		
		
	} //board_write()
	
	//컨트롤러 통해서 view로 가는 흐름 이해
	//board/board_write.jsp생성하기
	//매핑주소 이해 필요 !!
	
	//게시판 저장
	@RequestMapping(value="/board_write",method=RequestMethod.POST) //동일 매핑주소를 사용하면 메서드 방식으로 구분한다.
	//POST 방식으로 접근하는 매핑주소 처리 
	public ModelAndView board_write(BoardVO b, RedirectAttributes rttr) {
		/* BoardVO b는 board_write.jsp의 네임피라미터 이름과 BoardVO.java의 변수명이 같으면 b객체에 
		 * 글쓴이, 글제목, 글내용이 벌써 저장되어 있다.
		 */
		this.boardService.insertBoard(b); //게시판 저장
		rttr.addFlashAttribute("result","success");
		/*
		 * 백엔드 서버단에서 다른 매핑주소로 이동시 result키이름에 success문자를 담아서 전달한다.
		 * 주소창에 노출 안 되어서 보안상 좋다.
		 */
		return new ModelAndView("redirect:/board/board_list");
		/*
		 * ModelAndView 생성자 인자값에 올 수 있는 것 )
		 * 1. 뷰페이지 경로
		 * 2. redirect:/매핑주소 , 여기서는 매핑주소인 /board/board_list 게시판 목록보기 매핑주소로 이동 
		 */
	} //board_write() -> post 방식 
	
	//게시판 목록
	@GetMapping("/board_list") // get 으로 접근하는 매핑주소 처리 -> board_list매핑주소 등록 
	public String board_list(Model listM,HttpServletRequest request,@ModelAttribute
			BoardVO b) {
		
		/*페이징에 관련된 소스 시작*/
		int page=1; //현재 쪽번호
		int limit=10; // 한 페이지에 보여지는 목록 개수
		if(request.getParameter("page")!=null) { //get으로 전달된 쪽번호가 있는 경우 실행
			
			page=Integer.parseInt(request.getParameter("page"));
			// 쪽번호를 정수 숫자로 변경해서 저장 
		}
		b.setStartrow((page-1)*10+1); //시작 행 번호 
		b.setEndrow(b.getStartrow()+limit-1); //끝 행번호
		/* 페이징 소스 끝 */
		
		int totalCount = this.boardService.getTotalCount(); //총 레코드 개수
		List<BoardVO> blist = this.boardService.getBoardLsit(b); //게시물 목록 
		
		/*페이징 연산 소스 시작 */
		int maxpage = (int)((double)totalCount/limit+0.95);//총페이지수
	       int startpage = (((int)((double)page/10+0.9))-1)*10+1;//현재 페이지에 보여질 시작페이지
	       int endpage = maxpage;//현재 페이지에 보여질 마지막 페이지
	        
	       if(endpage>startpage+10-1) endpage=startpage+10-1;
	       //마지막페이지>시작페이지+10-1     마지막페이지=시작페이지+10-1
		/*페이징 연산 끝*/
		
	      
		listM.addAttribute("totalCount",totalCount); //totalCount키이름에 총레코드 개수 저장
		listM.addAttribute("blist",blist); //blist키이름에 목록 저장
		
		listM.addAttribute("startpage",startpage); // 시작페이지
		listM.addAttribute("endpage",endpage); //끝페이지 
		listM.addAttribute("maxpage",maxpage); //총페이지
		listM.addAttribute("page",page); //현재 쪽번호
		// - > 페이징에서 내가 본 쪽번호로 바로 이동하는 책갈피 기능을 구현하기 위한 것이다.
		
		return "board/board_list"; //뷰페이지 경로 - > /WEB-INF/views/board/board_list.jsp
		
	} //board_list()
	
	//내용보기+조회수증가+수정폼
	@RequestMapping("/board_cont")  //get or post로 접근하는 매핑주소를 처리, board_cont 매핑주소 등록
	public ModelAndView board_cont(@RequestParam("bno")int bno,int page, 
			String state,BoardVO b) {
		/*
		 * @RequestParam("bno")는 request.getParameter("bbs_no")와 기능이 같다.
		 * get으로 전달된 bbs_no 네임피라미터 값인 번호값을 가져온다.
		 *	int page, String state는 get으로 전달된 page,state 네임피라미터에 담겨져서 전달된 쪽번호와
		 *cont구분값을 가져온다.*/
		if(state.contentEquals("cont")) { //내용보기 일때만 조회수가 증가 
			b=this.boardService.getBoardCont(bno);
		}else {//수정폼일때는 조회수 증가 안 됨.
			b=this.boardService.getBoardCont2(bno);
		}
		String content=b.getContent().replace("\n", "<br>"); //textarea입력박스에서 엔터키를 친 부분을 줄바꿈 처리한다.
		ModelAndView cm=new ModelAndView();
		cm.addObject("page",page); //페이징에서 책갈피 기능때문에 쪽번호 저장
		cm.addObject("b",b);
		cm.addObject("content",content);
		
		if(state.equals("cont")) {//내용보기일 때 실행
			cm.setViewName("board/board_cont"); //뷰리졸브경로는 /WEB-INF/views/board/board_cont.jsp
		}else if(state.equals("edit")) {//수정폼일때
			cm.setViewName("board/board_edit");
		}
		return cm;
	}//board_cont()
	
	// 게시판 수정 완료
	@PostMapping("/board_edit_ok") //post로 접근하는 매핑주소 처리 
	public String board_edit_ok(BoardVO eb, int page, Model m) {
		/*
		 *  BoardVO eb라고 하면 네임피라미터 이름과 빈클래스 변수명이 같으면 eb객체에 bno,writer,title,
		 *  content 까지 저장 되어 있다.하지만 page는 빈클래스 변수명에 없기 때문에 int page로 별도로 가져와야 한다.
		 */
		this.boardService.editBoard(eb);
		
		m.addAttribute("page",page);
		m.addAttribute("bno",eb.getBno());
		m.addAttribute("state","cont");
		
		return "redirect:/board/board_cont"; //웹주소창에 다음과 같은 매핑주소가 실행된다.
		// board_cont?page=쪽번호&bno=번호 형태의 get방식으로 3개의 피라미터값이 전달된다.
	}//board_edit_ok()
	
	// 게시물 삭제
	@GetMapping("/board_del") //get으로 접근하는 매핑주소 처리
	public ModelAndView board_del(int page,int bno,RedirectAttributes rttr) {
		this.boardService.delBoard(bno); //번호 기준으로 삭제 
		rttr.addFlashAttribute("result","success");
		return new ModelAndView("redirect:/board/board_list?page="+page);
	}// board_del()
	
	
}
