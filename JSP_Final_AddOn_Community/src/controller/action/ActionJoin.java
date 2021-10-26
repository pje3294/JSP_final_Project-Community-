package controller.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.users.UsersDAO;
import model.users.UsersVO;

public class ActionJoin implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		response.setContentType("text/html;charset=UTF-8"); 

		UsersDAO usersDAO = new UsersDAO();
		UsersVO usersVO = new UsersVO();

		String name="";
		String id="";
		String pw="";
		String phone="";
		String gender="";
		String email="";
		String addr="";
		String birth="";
		String iconId="";
		
		name = request.getParameter("name");
		id = request.getParameter("id");
		pw = request.getParameter("pw");
		phone= request.getParameter("phone");
		gender = request.getParameter("gender");
		email = request.getParameter("email");
		birth = request.getParameter("birth");
		iconId =request.getParameter("iconId");
		addr= request.getParameter("addr");
		
		
		usersVO.setAddr(addr);
		usersVO.setBirth(birth);
		usersVO.setEmail(email);
		usersVO.setGender(gender);
		usersVO.setIconId(iconId);
		usersVO.setId(id);
		usersVO.setName(name);
		usersVO.setPhone(phone);
		usersVO.setPw(pw);
		
		System.out.println(usersVO);
		PrintWriter out =null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (usersDAO.insert(usersVO)) {
			out.println("<script>alert('회원가입에 성공하였습니다!');location.href='index.jsp'</script>");
			
		}else {
			
			out.println("<script>alert('회원가입에 성공하였습니다');location.href='index.jsp'</script>");
		}
		
				



		return forward;
	}

}
