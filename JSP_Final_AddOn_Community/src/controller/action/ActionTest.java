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
		
		
		int pageNum=0; // 초기에는 pageNum =1 페이지
		int pageLen = 0; // 총 길이를 구해줘야함  DAO 메서드 추가 
		int userNum= 0;
		String tTitle = "";
		String pageOrder= "date";
		if (request.getParameter("pageNum") !=null) {
			
		pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		}
		if (request.getParameter("tTitle") !=null ) { // 검색어 있는 경우 
			if (!request.getParameter("tTitle").equals(""))
			tTitle = request.getParameter("tTitle");
		}
		
		
	
		userVO.setUserNum(userNum); // 비로그인 상태 
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
		request.setAttribute("tTitle", tTitle); //검색 값
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
