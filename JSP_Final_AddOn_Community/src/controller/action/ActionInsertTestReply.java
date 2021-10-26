package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.test.TestReplyDAO;
import model.test.TestReplyVO;

public class ActionInsertTestReply implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;

		TestReplyDAO replyDAO = new TestReplyDAO();
		TestReplyVO reply = new TestReplyVO();
		
		int rId=0;
		int tId = Integer.parseInt(request.getParameter("tId"));
		int userNum = Integer.parseInt(request.getParameter("userNum"));
		String rContent = request.getParameter("rContent");
		String rWriter = request.getParameter("rWriter");
		int parentId = Integer.parseInt(request.getParameter("parentId"));

		reply.settId(tId);
		reply.setParentId(parentId);
		reply.setrContent(rContent);
		reply.setrWriter(rWriter);
		reply.setUserNum(userNum);
		System.out.println("ActionInsertTestReply.reply  : "+reply);
		if((rId=replyDAO.insert(reply))>0) {
			System.out.println("댓글 입력 성공!");
		}else {
			System.out.println("댓글 입력 실패!");
		}
		String path = "detailTest.do";
		path += "?tId="+tId;
		path += "&insertedRid="+rId;
		if (request.getParameter("pageNum") !=null) {
			path +="&pageNum="+request.getParameter("pageNum");
		}
		forward = new ActionForward();
		forward.setPath(path);
		forward.setRedirect(false);




		return forward;
	}

}
