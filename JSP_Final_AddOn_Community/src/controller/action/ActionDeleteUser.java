package controller.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.common.Action;
import controller.common.ActionForward;
import model.users.UsersDAO;
import model.users.UsersVO;

public class ActionDeleteUser implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		ActionForward forward = null;
		response.setContentType("text/html;charset=UTF-8");
		
		UsersDAO userDAO = new UsersDAO();
		UsersVO usersVO = new UsersVO();
		
		int userNum = Integer.parseInt(request.getParameter("userNum"));
		
		usersVO.setUserNum(userNum);
		
		PrintWriter out =null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (userDAO.delete(usersVO)) {
			HttpSession session = request.getSession();
			session.invalidate();
			out.println("<script>alert('Ż�� �Ϸ�Ǿ����ϴ�! �̿����ּż� �����մϴ�');location.href='index.jsp';</script>");
		}else {
			out.println("<script>alert('������ �����߽��ϴ�!');location.href='index.jsp';</script>");

		}
		
		
		return null;
	}

}
