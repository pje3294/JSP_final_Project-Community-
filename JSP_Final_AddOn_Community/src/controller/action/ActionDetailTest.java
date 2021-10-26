package controller.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Encoder.TextStream;

import controller.common.Action;
import controller.common.ActionForward;
import model.test.TestDAO;
import model.test.TestReplyDAO;
import model.test.TestReplySet;
import model.test.TestReplyVO;
import model.test.TestSet;
import model.test.TestVO;
import model.users.UsersVO;

public class ActionDetailTest implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		TestDAO testDAO = new TestDAO();
		TestReplyDAO testReplyDAO = new TestReplyDAO();
		TestVO test = new TestVO();
		TestReplyVO reply = new TestReplyVO();
		UsersVO user =null;
		
		ArrayList<TestReplySet> testReplySets = null; // ��۹� ���� ��Ʈ ��Ʈ
		
		int pageNum = 0; // ��� ����¡ 
		int pageLen = 0; // ��� ��ü ����
		if(request.getParameter("pageNum") !=null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		 // ��� ã�� �߰��� �κ� ------------------------------------------------------------
			if(request.getAttribute("pageNum")!=null) { 
				
				pageNum = (Integer) request.getAttribute("pageNum");
			}
			 // ��� ã�� �߰��� �κ� ------------------------------------------------------------		
		
		int tId = Integer.parseInt(request.getParameter("tId"));
		test.settId(tId);
		
		
		HttpSession session= request.getSession();
		
		user = (UsersVO) session.getAttribute("user");
		if ( session.getAttribute("user")==null) { //��α��� �϶�
			user = new UsersVO();
			user.setId("");
			user.setUserNum(0);
		}
		
		test = testDAO.getDBData(test,user); 
		
		reply.settId(tId);
		UsersVO Uvo = new UsersVO();
		Uvo.setUserNum(0);
		
		testReplySets = testReplyDAO.getDBList( pageNum, reply);
		if (testReplySets.size() != 0) {
		pageLen = testReplySets.get(0).getTestReCnt();
		}
		// ��ȸ�� ���� !!
		if( (user.getUserNum() != test.getUserNum() ) && (request.getParameter("addHit") != null)){
		int hit = test.gettHit();  
		test.settHit(++hit);
		testDAO.addHit(test);
		}
		///////////////////////////////
		
		
		request.setAttribute("test", test);
		request.setAttribute("testReplySets", testReplySets);
		request.setAttribute("pageLen", pageLen);
		request.setAttribute("pageNum", pageNum);
		
		
		
		forward = new ActionForward();
		forward.setPath("detailTest.jsp");
		forward.setRedirect(false);
		
		
		
		
		return forward;
	}

}
