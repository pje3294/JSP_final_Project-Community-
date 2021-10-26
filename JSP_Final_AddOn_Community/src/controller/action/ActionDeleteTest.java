package controller.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.test.TestDAO;
import model.test.TestVO;

public class ActionDeleteTest implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=UTF-8");
		
		ActionForward forward = null;
		
		TestDAO testDAO = new TestDAO();
		TestVO test = new TestVO();
		
		int tId = Integer.parseInt(request.getParameter("tId"));
		
		test.settId(tId);
		
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(testDAO.delete(test)) {
			out.println("<script>alert('삭제가 완료되었습니다!');location.href='test.do';</script>");
		}
		else {
			out.println("<script>alert('삭제가 실패했습니다!');location.href='test.do';</script>");
		}
		
		
		
		return forward;
	}
	

}
