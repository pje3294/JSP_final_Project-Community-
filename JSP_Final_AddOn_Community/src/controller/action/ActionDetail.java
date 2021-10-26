package controller.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.common.Action;
import controller.common.ActionForward;
import model.board.BoardDAO;
import model.board.BoardVO;
import model.board.ReplyDAO;
import model.board.ReplySet;
import model.board.ReplyVO;
import model.users.UsersVO;

public class ActionDetail implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		ActionForward forward = null;
		
		BoardDAO boardDAO = new BoardDAO();
		ReplyDAO replyDAO = new ReplyDAO();
		UsersVO userVO =  new UsersVO();
		ReplyVO replyVO = new ReplyVO();
		
		
		
		int bId = Integer.parseInt( request.getParameter("bId"));
		int pageNum = 0; // ´ñ±Û ÆäÀÌÂ¡
		int pageLen = 0; // ´ñ±Û °´¼ö
		if( request.getParameter("pageNum")!=null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		 // ´ñ±Û Ã£±â Ãß°¡µÈ ºÎºÐ ------------------------------------------------------------
			if(request.getAttribute("pageNum")!=null) { 
				
				pageNum = (Integer) request.getAttribute("pageNum");
			}
			 // ´ñ±Û Ã£±â Ãß°¡µÈ ºÎºÐ ------------------------------------------------------------		
		
		BoardVO board = new BoardVO();
		board.setbId(bId);
		
		HttpSession session= request.getSession();
		
		if (session.getAttribute("user") !=null) {
			UsersVO user = (UsersVO) session.getAttribute("user");
			userVO.setUserNum(user.getUserNum());
		} 
		
		board = boardDAO.getDBData(userVO,board); 
		
	//	userVO.setUserNum(0);
		replyVO.setbId(bId);
		
		ArrayList<ReplySet> datas = replyDAO.getDBList(replyVO, pageNum);
		List<ReplySet> replySets = datas.subList(0, datas.size()-1);
		ReplySet replySet = datas.get(datas.size()-1);
		pageLen = replySet.getReplyCnt();
		
		System.out.println("ActionDetail pageLen"+ pageLen);
		
//		for (ReplySet v : replySets) {
//			ReplyVO rv = v.getRvo();
//			ArrayList<ReplyVO> rrList = v.getRrlist();
//			System.out.println("-----------------------");
//			System.out.println("´ñ±Û : "+ rv.toString());
//			
//			System.out.println("´ë´ñ±Û : ");
//		if (rrList != null) {
//			for (ReplyVO vo : rrList) {
//				System.out.println(vo.getrContent());
//			}
//			
//			}
//			
//		}
		// Á¶È¸¼ö Áõ°¡ !!
		if((userVO.getUserNum() != board.getUserNum()) && (request.getParameter("addHit") != null)){
		int hit = board.getbHit();  
		board.setbHit(++hit);
		boardDAO.addHit(board);
		}
		///////////////////////////////
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pageLen", pageLen);
		request.setAttribute("board", board);
		request.setAttribute("replySets", replySets);
		System.out.println(board);
		
		
		forward = new ActionForward();
		forward.setPath("detail.jsp");
		forward.setRedirect(false);
		
		
		
		return forward;
	}

}
