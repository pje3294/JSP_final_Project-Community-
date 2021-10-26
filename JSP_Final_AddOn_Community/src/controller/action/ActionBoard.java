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

public class ActionBoard implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		
		BoardDAO boardDAO = new BoardDAO();
		
		int userNum=0;
		
		UsersVO userVO = new UsersVO();
		userVO.setUserNum(userNum);
		if(request.getParameter("selUserNum")!=null) {
			userNum = Integer.parseInt(request.getParameter("selUserNum"));
			//request.setAttribute("selUser", userNum);
			userVO.setUserNum(userNum);
			
		}
		BoardVO boardVO = new BoardVO();
		int announcePageNum =0;
		int boardPageNum=0;
		int announceLen = 0;
		int boardLen = 0;
		String pageOrder;
		String bTitle ="";
		if (request.getParameter("announcePageNum") !=null) {
			announcePageNum = Integer.parseInt(request.getParameter("announcePageNum"));
			
		}
		if (request.getParameter("boardPageNum") !=null) {
			boardPageNum = Integer.parseInt(request.getParameter("boardPageNum"));
			
		}
		if (request.getParameter("bTitle") !=null) {
			bTitle = request.getParameter("bTitle");
		}
		System.out.println(announcePageNum);
		boardVO.setbCtgr("announce");
		
		pageOrder = "date";
		
		BoardSet bset = boardDAO.getDBList(userVO, boardVO,pageOrder, announcePageNum);
		ArrayList<BoardVO> announceList = bset.getBlist();
		announceLen = bset.getBoardCnt();
		
		
		if (request.getParameter("pageOrder") !=null) {
			pageOrder = request.getParameter("pageOrder");
		}
	
		
		boardVO.setbCtgr("board");
		boardVO.setbTitle(bTitle);
		
		bset = boardDAO.getDBList(userVO, boardVO,pageOrder, boardPageNum);
		ArrayList<BoardVO> boardList = bset.getBlist();
		boardLen = bset.getBoardCnt();
	
		request.setAttribute("announceList", announceList);
		request.setAttribute("boardList", boardList);
		request.setAttribute("announcePageNum", announcePageNum);
		request.setAttribute("boardPageNum", boardPageNum);
		request.setAttribute("announceLen", announceLen);
		request.setAttribute("boardLen",boardLen);
		request.setAttribute("bTitle", bTitle);
		request.setAttribute("pageOrder", pageOrder);
		request.setAttribute("selUserNum", userNum);
	
		
		forward = new ActionForward();
		forward.setPath("board.jsp");
		if (request.getParameter("myListCtgr") !=null) {
			System.out.println("ActionBoard 클래스 mypage 가는 중 ");
			forward.setPath("myPage.jsp");
			request.setAttribute("myList", boardList);
		}
		forward.setRedirect(false);
		
		
		return forward;
	}
	

}
