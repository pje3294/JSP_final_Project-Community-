package controller.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;

import controller.common.Action;
import controller.common.ActionForward;
import mail.SendEmail;
import model.users.UsersDAO;
import model.users.UsersVO;

public class ActionFindPw implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8"); 
		SendEmail sendEamil = new SendEmail();
		
		UsersDAO userDAO = new UsersDAO() ;
		UsersVO vo = new UsersVO();
		
		String id = request.getParameter("id");
		vo.setId(id);
		
		PrintWriter out =null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (userDAO.getDBData(vo) ==null) { //�ش� ���̵���� 
			out.println("<script>alert('�ش� ���̵�� �������� �ʽ��ϴ�!'); history.go(-1);</script>");
			return null;
		}
		
		UsersVO user = userDAO.getDBData(vo);
		String email = user.getEmail();
		String tempPw =RandomStringUtils.randomAlphanumeric(10);
		user.setPw(tempPw);
		if(userDAO.update(user)) {
			System.out.println("�ӽ� ��й�ȣ�� ������Ʈ ���� ");
		} else {
			System.out.println("�ӽ� ��й�ȣ�� ������Ʈ ���� ");
			
		}
		
		if(sendEamil.sendTempPw(email, tempPw)) {
			out.println("<script>alert('�ӽ� ��й�ȣ�� ���۵Ǿ����ϴ�!'); location.href='login.jsp';</script>");
		} else {
			out.println("<script>alert('�ӽ� ��й�ȣ ������ �����߽��ϴ�!'); location.href='login.jsp';</script>");
		}
		
		
		
		
		
		
		
		
		
		return null;
	}

}
