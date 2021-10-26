package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.board.ReplyDAO;
import model.board.ReplyVO;

public class FindReply implements Action {

	static final int paging = 10;
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("FindReply 객체 실행 ");
		ReplyVO replyVO = new ReplyVO();
		ReplyDAO replyDAO = new ReplyDAO();
		
		
		
		int bId = Integer.parseInt(request.getParameter("bId"));  // 찾는 댓글이 달린 게시글 
		int parentId = Integer.parseInt(request.getParameter("parentId"));
		
		int findId = Integer.parseInt(request.getParameter("findId")); // 찾는 댓글의 rid
	
	
		replyVO.setbId(bId);
		replyVO.setrId(findId);
		
		if (parentId !=0) {
			replyVO.setrId(parentId);
		}
		
		
		int replyOrder = replyDAO.replyOrder(replyVO);
		
		System.out.println("replyOrder  : "+ replyOrder);
		
		
		
		int pageNum= (replyOrder-1)/paging;
		
		
		
		System.out.println("FindReply pageNum : "+pageNum);
		
		request.setAttribute("pageNum", pageNum);
		
		
		ActionForward forward = new ActionForward();
		
		forward.setPath("detail.do");
		forward.setRedirect(false);
		 
		
		// TODO Auto-generated method stub
		return forward;
	}
	
	

}
