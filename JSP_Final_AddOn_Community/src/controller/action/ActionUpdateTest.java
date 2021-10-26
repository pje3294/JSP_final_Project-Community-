package controller.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.test.TestDAO;
import model.test.TestVO;

public class ActionUpdateTest implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		ActionForward forward = null;
		response.setContentType("text/html; charset=UTF-8");
		TestDAO testDAO=  new TestDAO();
		TestVO test =new TestVO();
		
		int tId = Integer.parseInt(request.getParameter("tId"));
		
		String tLang = request.getParameter("tLang");
		String tContent =request.getParameter("tContent");
		String tEx = request.getParameter("tEx");
		String tAnswer = request.getParameter("tAnswer");
		String tTitle = request.getParameter("tTitle");
		
		test.settId(tId);
		test.settTitle(tTitle);
		test.settLang(tLang);
		test.settContent(tContent);
		test.settAnswer(tAnswer);
		test.settEx(tEx);
		
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ActionUpdateTest");
		if(testDAO.update(test)) {
			out.println("<script>alert('글수정이 완료되었습니다!');location.href='detailTest.do?tId="+tId+"'</script>");
			
		}else {
			out.println("<script>alert('글수정이 실패했습니다!');location.href='detailTest.do?tId="+tId+"'</script>");

		}		
		
		
		return forward;
	}
	
	

}
