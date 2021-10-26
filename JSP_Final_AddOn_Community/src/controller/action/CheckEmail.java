package controller.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.common.Action;
import controller.common.ActionForward;

public class CheckEmail implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		String inputNum = request.getParameter("inputNum");
		
		String checkNum  =(String) session.getAttribute("checkNum");
		
		if (inputNum.equals(checkNum)) {
			out.print("success");
		}else {
			out.print("fail");
		}		
		
		
		
		return null;
	}

}
