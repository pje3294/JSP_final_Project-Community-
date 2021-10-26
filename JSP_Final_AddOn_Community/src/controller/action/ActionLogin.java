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

public class ActionLogin implements Action {

	@SuppressWarnings("null")
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		ActionForward forward = null;
		UsersDAO userDAO =new UsersDAO();
		UsersVO userVO = new UsersVO();
		
		String userID = request.getParameter("userId");
		String userPw  = request.getParameter("userPw");
		
		userVO.setId(userID);
		userVO.setPw(userPw);
		
		
		
		
		if(!userDAO.login(userVO)) {
			try {
				PrintWriter out = response.getWriter();
				out.println("<script>alert('�α��ο� �����Ͽ����ϴ�!');history.go(-1)</script>");
				return forward;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		userVO = userDAO.getDBData(userVO); //�α��� ������ ���� �����͸� ��� ������
		System.out.println("�α��� ���� UserVO : "+userVO);
		
		
		HttpSession session =request.getSession();
		
		//������ ��� 
		
		
		String managerId =request.getServletContext().getInitParameter("managerId");
		String managerPw = request.getServletContext().getInitParameter("managerPw");
		System.out.println(request.getServletPath());
		System.out.println("managerId : "+managerId);
		System.out.println("managerPw : "+managerPw);
		System.out.println("userID  : " + userVO.getId());
		System.out.println("userPW  : " + userVO.getPw());
		
		if(
			userVO.getId().equals(managerId)  &&
			userVO.getPw().equals(managerPw)
			
			) {
			System.out.println("-------������ ��� !!-------");
			session.setAttribute("manager", userVO);
		}
		
		session.setAttribute("user", userVO);
		forward = new ActionForward();
		forward.setPath("index.jsp"); // ���߿� �����ؾ� ��! ���� �������� �� �� �ֵ���!
		forward.setRedirect(false);
				
		
		return forward;
	}

}
