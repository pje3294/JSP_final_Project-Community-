package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.test.TestReplyDAO;
import model.test.TestReplyVO;

public class ActionDeleteTestReply implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		TestReplyDAO replyDAO= new TestReplyDAO();
		TestReplyVO reply = new TestReplyVO();
		
		int rId = Integer.parseInt(request.getParameter("rId"));
		int tId = Integer.parseInt(request.getParameter("tId"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int parentId = Integer.parseInt(request.getParameter("parentId"));
		String deleteAt = request.getParameter("deleteAt");
		
		reply.setrId(rId);
		reply.settId(tId);
		reply.setParentId(parentId);
		reply.setDeleteAt(deleteAt);
		
		if(replyDAO.delete(reply)) {
			System.out.println("삭제 성공");
		} else {
			System.out.println("삭제 실패");
		}
		
		
		String path= "detailTest.do";
		path += "?tId="+tId;
		path += "&pageNum="+pageNum;
		
		forward.setPath(path);
		forward.setRedirect(false);
		return forward;
	}

}
