package controller.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.test.TestDAO;
import model.test.TestVO;

public class ActionRating implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		ActionForward forward = null;
		response.setContentType("text/html; charset=UTF-8");
		TestDAO testDAO=  new TestDAO();
		TestVO test =new TestVO();
		
		int tId = Integer.parseInt(request.getParameter("tId"));
		int tTotal = Integer.parseInt(request.getParameter("tTotal"));
		
		test.settId(tId);
		test.settTotal(tTotal);

		
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(testDAO.rating(test)) {
			out.println("<script>alert('별점 추가가 완료되었습니다!');location.href='detailTest.do?tId="+tId+"'</script>");
			
		}else {
			out.println("<script>alert('별점 추가가 실패했습니다!');location.href='detailTest.do?tId="+tId+"'</script>");

		}		
		
		
		return forward;
	}

}
