package controller.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.board.ReplyDAO;
import model.board.ReplyVO;
import model.test.TestDAO;
import model.test.TestReplyDAO;
import model.test.TestReplyVO;
import model.test.TestVO;

public class ActionUpdateTestReply implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = new ActionForward();

		TestReplyDAO replyDAO = new TestReplyDAO();
		TestReplyVO reply = new TestReplyVO();

		int rId= Integer.parseInt(request.getParameter("rId"));
		int tId= Integer.parseInt(request.getParameter("tId"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		String rContent = request.getParameter("rContent");

		reply.setrId(rId);
		reply.setrContent(rContent);

		if(replyDAO.update(reply)) {
			System.out.println("수정 성공");
		}else {
			System.out.println("수정 실패");
		}

		String path= "detailTest.do";
		path  += "?tId="+tId;
		path += "&pageNum="+pageNum;	

		forward.setPath(path);
		forward.setRedirect(false);

		return forward;
	}

}
