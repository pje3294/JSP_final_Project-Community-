package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.test.TestReplyDAO;
import model.test.TestReplyVO;

public class FindTestReply implements Action {

	static final int paging = 10;  // 페이징 단위
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("FindTestReply 객체 실행 ");
		TestReplyVO replyVO = new TestReplyVO();
		TestReplyDAO replyDAO = new TestReplyDAO();
		
		
		
		int tId = Integer.parseInt(request.getParameter("tId"));  // 찾는 댓글이 달린 게시글 
		int parentId = Integer.parseInt(request.getParameter("parentId"));
		
		int findId = Integer.parseInt(request.getParameter("findId")); // 찾는 댓글의 rid
	
	
		replyVO.settId(tId);
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
		
		forward.setPath("detailTest.do");
		forward.setRedirect(false);
		 
		
		// TODO Auto-generated method stub
		return forward;
	}
	
	

}
