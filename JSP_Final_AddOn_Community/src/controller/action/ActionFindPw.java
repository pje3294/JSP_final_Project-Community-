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
		
		if (userDAO.getDBData(vo) ==null) { //해당 아이디없음 
			out.println("<script>alert('해당 아이디는 존재하지 않습니다!'); history.go(-1);</script>");
			return null;
		}
		
		UsersVO user = userDAO.getDBData(vo);
		String email = user.getEmail();
		String tempPw =RandomStringUtils.randomAlphanumeric(10);
		user.setPw(tempPw);
		if(userDAO.update(user)) {
			System.out.println("임시 비밀번호로 업데이트 성공 ");
		} else {
			System.out.println("임시 비밀번호로 업데이트 실패 ");
			
		}
		
		if(sendEamil.sendTempPw(email, tempPw)) {
			out.println("<script>alert('임시 비밀번호가 전송되었습니다!'); location.href='login.jsp';</script>");
		} else {
			out.println("<script>alert('임시 비밀번호 전송이 실패했습니다!'); location.href='login.jsp';</script>");
		}
		
		
		
		
		
		
		
		
		
		return null;
	}

}
