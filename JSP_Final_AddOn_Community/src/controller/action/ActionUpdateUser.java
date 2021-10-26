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

public class ActionUpdateUser implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		response.setContentType("text/html; charset=UTF-8");
		
		UsersDAO usersDAO = new UsersDAO();
		UsersVO  usersVO = new UsersVO();
		
		int userNum=0;
		String name="";
		String id="";
		String pw="";
		String phone="";
		String gender="";
		String email="";
		String addr="";
		String birth="";
		String iconId="";
		
		userNum = Integer.parseInt(request.getParameter("userNum"));
		name = request.getParameter("name");
		id = request.getParameter("id");
		pw = request.getParameter("pw");
		phone= request.getParameter("phone");
		gender = request.getParameter("gender");
		email = request.getParameter("email");
		birth = request.getParameter("birth");
		iconId =request.getParameter("iconId");
		addr= request.getParameter("addr");
		
		usersVO.setUserNum(userNum);
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
		
		String url="myPage.do?"
				+ "selUserNum="+userNum
				+ "&myListCtgr=board";
		if (usersDAO.update(usersVO)) {
			HttpSession session = request.getSession();
			UsersVO user = (UsersVO) session.getAttribute("user");
			user = usersDAO.getDBData(usersVO);
			session.setAttribute("user", user);
			
			out.println("<script>alert('회원 정보 수정이 완료되었습니다!');location.href='"+url+"'</script>");
			
		}else {
			
			out.println("<script>alert('회원 정보 수정에 실패했습니다!');location.href='"+url+"'</script>");
		}
		
		
		
		
		
		return forward;
	}
	
	

}
