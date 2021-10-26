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

		String filePath = request.getSession().getServletContext().getRealPath("images"); //���� �̹��� ���
		
		int userNum=Integer.parseInt(request.getParameter("userNum"));
		String iconId=(String)request.getParameter("filename");
		
		userVO.setUserNum(userNum);
		userVO.setIconId(iconId);

		String url="myPage.do?"
				+ "selUserNum="+userNum
				+ "&myListCtgr=board";
		forward.setPath(url);
		
		if(userDAO.deleteImg(userVO)) {
			//���� ����
			filePath += "/"+userVO.getIconId();
			File file = new File(filePath);	//���� ����
			if(file.exists() && !userVO.getIconId().equals("default.png")) {				//������ ������ ���� 
				file.delete();
			}
			HttpSession session = request.getSession();
			UsersVO user = userDAO.getDBData(userVO);
			session.setAttribute("user", user);
			forward.setRedirect(false);
			forward.setPath(url);
		}
		else {
			System.out.println("���� ���� ����");
			forward.setRedirect(false);
			forward.setPath(url);
		}
		return forward;
	}


}
