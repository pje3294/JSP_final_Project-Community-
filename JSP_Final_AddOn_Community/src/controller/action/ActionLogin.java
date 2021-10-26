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
				out.println("<script>alert('로그인에 실패하였습니다!');history.go(-1)</script>");
				return forward;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		userVO = userDAO.getDBData(userVO); //로그인 성공시 유저 데이터를 모두 가져옴
		System.out.println("로그인 성공 UserVO : "+userVO);
		
		
		HttpSession session =request.getSession();
		
		//관리자 모드 
		
		
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
			System.out.println("-------관리자 모드 !!-------");
			session.setAttribute("manager", userVO);
		}
		
		session.setAttribute("user", userVO);
		forward = new ActionForward();
		forward.setPath("index.jsp"); // 나중에 수정해야 함! 이전 페이지로 갈 수 있도록!
		forward.setRedirect(false);
				
		
		return forward;
	}

}
