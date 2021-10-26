package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.board.ReplyDAO;
import model.board.ReplyVO;

public class ActionDeleteReply implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		ReplyDAO replyDAO= new ReplyDAO();
		ReplyVO reply = new ReplyVO();
		
		int rId = Integer.parseInt(request.getParameter("rId"));
		int bId = Integer.parseInt(request.getParameter("bId"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int parentId = Integer.parseInt(request.getParameter("parentId"));
		String deleteAt = request.getParameter("deleteAt");
		
				
		reply.setrId(rId);
		reply.setbId(bId);
		reply.setParentId(parentId);
		reply.setDeleteAt(deleteAt);
		
		
		if(replyDAO.delete(reply)) {
			System.out.println("삭제 성공");
		} else {
			System.out.println("삭제 실패");
		}
		
		
		String path= "detail.do";
		path += "?bId="+bId;
		path += "&pageNum="+pageNum;
		
		forward.setPath(path);
		forward.setRedirect(false);
		return forward;
	}
	
	

}
