package controller.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.common.ActionForward;
import model.users.UsersDAO;
import model.users.UsersVO;

public class ActionImgDelete {

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = new ActionForward();

		UsersDAO userDAO = new UsersDAO();
		UsersVO userVO = new UsersVO();

		String filePath = request.getSession().getServletContext().getRealPath("images"); //서버 이미지 경로
		
		int userNum=Integer.parseInt(request.getParameter("userNum"));
		String iconId=(String)request.getParameter("filename");
		
		userVO.setUserNum(userNum);
		userVO.setIconId(iconId);

		String url="myPage.do?"
				+ "selUserNum="+userNum
				+ "&myListCtgr=board";
		forward.setPath(url);
		
		if(userDAO.deleteImg(userVO)) {
			//파일 삭제
			filePath += "/"+userVO.getIconId();
			File file = new File(filePath);	//파일 생성
			if(file.exists() && !userVO.getIconId().equals("default.png")) {				//파일이 있을시 삭제 
				file.delete();
			}
			HttpSession session = request.getSession();
			UsersVO user = userDAO.getDBData(userVO);
			session.setAttribute("user", user);
			forward.setRedirect(false);
			forward.setPath(url);
		}
		else {
			System.out.println("파일 삭제 실패");
			forward.setRedirect(false);
			forward.setPath(url);
		}
		return forward;
	}


}
