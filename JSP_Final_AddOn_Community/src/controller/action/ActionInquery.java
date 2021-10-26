package controller.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import mail.SendEmail;

public class ActionInquery implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		response.setContentType("text/html; charset=UTF-8");
	
		SendEmail sendEmail = new SendEmail();
		String subject = request.getParameter("subject");
		String inqueryText = request.getParameter("inqueryText");
		
		String text = " subject : "+subject +"\n"
				      +inqueryText; 
		
		PrintWriter out =null;
		
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(sendEmail.sendInquery(text)) {
			out.println("<script>alert('문의 접수가 완료되었습니다. 항상 발전하는 Add-on 이 되겠습니다.');location.href='index.jsp';</script>");
		}else {
			out.println("<script>alert('문의 접수가 실패했습니다. 항상 발전하는 Add-on 이 되겠습니다.');location.href='index.jsp';</script>");

		}
			
			
			
		
		
		
		return null;
	}

}
