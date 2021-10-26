package controller.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.board.BoardDAO;
import model.board.BoardSet;
import model.board.BoardVO;
import model.users.UsersVO;

public class ActionQuestion implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		ActionForward forward = new ActionForward();
		BoardDAO boardDAO = new BoardDAO();
		BoardVO boardVO = new BoardVO();
		UsersVO userVO = new UsersVO();
		
		int pageNum=0; // 초기에는 pageNum =1 페이지
		int pageLen = 0; // 총 길이를 구해줘야함  DAO 메서드 추가 
		String pageOrder ="date";
		String bTitle=""; // 검색
		
		if (request.getParameter("pageNum") !=null) {
			
		pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		}
		if (request.getParameter("pageOrder") !=null) {
		
			pageOrder =request.getParameter("pageOrder");
		}
		if(request.getParameter("bTitle") !=null) {
			bTitle = request.getParameter("bTitle");
		}
		
		
		
		int userNum=0;
		userVO.setUserNum(userNum);  
		
		if (request.getParameter("selUserNum")!=null) { // 마이페이지 일때!
			userNum = Integer.parseInt( request.getParameter("selUserNum"));
			userVO.setUserNum(userNum);
		}
		boardVO.setbCtgr("question"); // 카테고리 설정 !
		boardVO.setbTitle(bTitle);
		
		BoardSet bset = boardDAO.getDBList(userVO, boardVO,pageOrder, pageNum);
		
		ArrayList<BoardVO> questions = bset.getBlist();
		pageLen = bset.getBoardCnt();
		System.out.println("ActionQuestion pageLen : "+pageLen);
		
		request.setAttribute("questions", questions);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pageLen", pageLen);
		request.setAttribute("bTitle", bTitle);
		request.setAttribute("pageOrder", pageOrder);
		request.setAttribute("selUserNum", userNum);
		
		
		forward.setPath("question.jsp");
		if (request.getParameter("myListCtgr") !=null) {
			System.out.println("ActionQuestion mypage.jsp 가는 중");
			forward.setPath("myPage.jsp");
			request.setAttribute("myList", questions);
		}
		forward.setRedirect(false);
		
		
		
		
		
		return forward;
	}
	

}
