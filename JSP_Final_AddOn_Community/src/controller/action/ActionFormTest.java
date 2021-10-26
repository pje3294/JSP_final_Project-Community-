package controller.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.test.TestDAO;
import model.test.TestVO;

public class ActionFormTest implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=UTF-8");
		ActionForward forward = null;
		TestVO testVO = new TestVO();
		TestDAO testDAO = new TestDAO();
		
		int userNum = Integer.parseInt(request.getParameter("userNum"));
		String tTitle = request.getParameter("tTitle");
		String tWriter = request.getParameter("tWriter");
		String tLang = request.getParameter("tLang");
		String tContent = request.getParameter("tContent");
		String tEx = request.getParameter("tEx");
		String tAnswer = request.getParameter("tAnswer");
		
		testVO.setUserNum(userNum);
		testVO.settTitle(tTitle);
		testVO.settWriter(tWriter);
		testVO.settLang(tLang);
		testVO.settContent(tContent);
		testVO.settEx(tEx);
		testVO.settAnswer(tAnswer);
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(testDAO.insert(testVO)) {
			out.println("<script>alert('글작성이 완료되었습니다!');location.href='test.do'</script>");
			
		}else {
			out.println("<script>alert('글작성이 실패했습니다!');location.href='test.do'</script>");

		}
		
		return forward;
	}

}
