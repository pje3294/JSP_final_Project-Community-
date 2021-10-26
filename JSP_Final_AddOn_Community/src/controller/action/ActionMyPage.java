package controller.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.common.Action;
import controller.common.ActionForward;
import model.board.MyReplySet;
import model.board.ReplyDAO;
import model.board.ReplyVO;
import model.test.TestMySet;
import model.test.TestReplyDAO;
import model.test.TestReplyVO;
import model.users.UsersDAO;
import model.users.UsersVO;

public class ActionMyPage implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		ActionForward forward = new ActionForward();
		
		String myListCtgr = "question" ;//�ʱⰪ 
		
		if (request.getParameter("myListCtgr")!=null) {
			
			myListCtgr = request.getParameter("myListCtgr");
		};
		
		String path="";
		if (myListCtgr.equals("test")) {
			path += "test.do";
		} else if (myListCtgr.equals("board")) {
			path +="board.do";
			
		}else {
			path+="question.do";
		}
		
		
		forward.setPath(path);
		forward.setRedirect(false);
		 // ���������� ��� �κ�! 
		int replyPageNum=0;
		int userNum = Integer.parseInt(request.getParameter("selUserNum")) ;
		HttpSession session= request.getSession();
		UsersVO user =(UsersVO) session.getAttribute("user"); // �α����� ����
		UsersDAO userDAO = new UsersDAO();
		
		if (user ==null) { // �α����� ������ ���� ��
			
			UsersVO selUser = new UsersVO();
			selUser.setUserNum(userNum);
			selUser = userDAO.getDBData(selUser);
			
			request.setAttribute("selUser", selUser);
			return forward ;
		}
		else if (user.getUserNum() != userNum) { // �α����� ������ ������ �ƴ� �ٸ� ����� ������������ �������� ��
			UsersVO selUser = new UsersVO();
			selUser.setUserNum(userNum);
			selUser = userDAO.getDBData(selUser);
			
			request.setAttribute("selUser", selUser);
			return forward;
		}
		// ������ ������ ���� ���� ��  ��� ����Ʈ�� ����! ------------------------------------------------
		
		if (request.getParameter("replyPageNum") !=null && request.getParameter("replyPageNum") !="") {
			replyPageNum = Integer.parseInt(request.getParameter("replyPageNum"));
		}

		String replyCtgr ="reply";
		int replyLen=0;
		
		if (request.getParameter("replyCtgr") !=null && request.getParameter("replyCtgr") != "") {
			replyCtgr = request.getParameter("replyCtgr");
		}
		
		if (replyCtgr.equals("reply")) {
		ReplyDAO replyDAO = new ReplyDAO();
		MyReplySet myReplySet = replyDAO.myReply(user, replyPageNum);
		ArrayList<ReplyVO> myReplies =  myReplySet.getRlist();
		replyLen = myReplySet.getReplyCnt();
		
		request.setAttribute("myReplies", myReplies);
		
	//	System.out.println("ActionMyPage replyLen : "+replyLen);
		}
		else if(replyCtgr.equals("testReply")) {
		
		TestReplyDAO testReplyDAO = new TestReplyDAO();
		TestMySet testMySet = testReplyDAO.myReply(user, replyPageNum);
		ArrayList<TestReplyVO> myTestReplies = testMySet.getRlist();
		replyLen = testMySet.getTestRecnt();
		request.setAttribute("myTestReplies", myTestReplies);
		
	//	request.setAttribute("myListCtgr", myListCtgr); // ���̸����� �� ī�װ�
		}
		request.setAttribute("replyPageNum", replyPageNum);
		request.setAttribute("replyLen", replyLen);
		request.setAttribute("selUser", user);
		
		
		
		return forward;
	}
	
}
