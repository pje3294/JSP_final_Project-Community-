package controller.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.test.TestDAO;
import model.test.TestSet;
import model.test.TestVO;
import model.users.UsersVO;

public class ActionTest implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		ActionForward forward = new ActionForward();
		TestDAO testDAO= new TestDAO();
		
		UsersVO userVO = new UsersVO();
		
		
		int pageNum=0; // �ʱ⿡�� pageNum =1 ������
		int pageLen = 0; // �� ���̸� ���������  DAO �޼��� �߰� 
		int userNum= 0;
		String tTitle = "";
		String pageOrder= "date";
		if (request.getParameter("pageNum") !=null) {
			
		pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		}
		if (request.getParameter("tTitle") !=null ) { // �˻��� �ִ� ��� 
			if (!request.getParameter("tTitle").equals(""))
			tTitle = request.getParameter("tTitle");
		}
		
		
	
		userVO.setUserNum(userNum); // ��α��� ���� 
		if(request.getParameter("selUserNum")!=null) {
			userNum = Integer.parseInt(request.getParameter("selUserNum"));
			
			userVO.setUserNum(userNum);
		}
		if(request.getParameter("pageOrder") !=null) {
			pageOrder = request.getParameter("pageOrder");
		}
		
		
		TestSet datas = testDAO.getDBList(tTitle, pageNum, userVO,pageOrder);
		ArrayList<TestVO> tests  = datas.getTlist();
		pageLen = datas.getTestCnt();
		
		
		request.setAttribute("tests", tests);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pageLen", pageLen);
		request.setAttribute("tTitle", tTitle); //�˻� ��
		request.setAttribute("pageLen", pageLen);
		request.setAttribute("pageOrder", pageOrder);
		request.setAttribute("selUserNum", userNum);
		
		forward.setPath("test.jsp");
		if(request.getParameter("myListCtgr")!=null) {
			forward.setPath("myPage.jsp");
			request.setAttribute("myList", tests);
		}
		forward.setRedirect(false);
		
		
		
		
		
		return forward;
		
	}
	
}
