package controller.action;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import controller.common.Action;
import controller.common.ActionForward;
import model.users.UsersDAO;
import model.users.UsersVO;

public class ActionImgUpload implements Action {

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = new ActionForward();

		UsersDAO userDAO = new UsersDAO();
		UsersVO userVO = new UsersVO();

		//���� ���
		String filePath = request.getSession().getServletContext().getRealPath("images"); 
		String encoding = "UTF-8";


		// �ִ� 16Mbyte ����
		int maxSize = 16 * 1024 * 1024; 
		MultipartRequest multi;
		
		
		
		try {

			multi = new MultipartRequest(request, filePath, maxSize, encoding,	// ���� ����	
					new DefaultFileRenamePolicy()); // �����̸� �ڵ� ������	
			int userNum=Integer.parseInt(multi.getParameter("userNum"));
			String iconId=(String)multi.getFilesystemName("filename");
			
			userVO.setIconId(iconId); // ������ ������ �̸� ��������
			userVO.setUserNum(userNum);
			String url="myPage.do?"
					+ "selUserNum="+userNum
					+ "&myListCtgr=board";
			forward.setPath(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		UsersVO uvo = userDAO.getDBData(userVO);
		System.out.println(uvo);
		// reupload �� ���ϻ���
		if (uvo.getIconId() != null) {
			//���� ����
			filePath += "/"+uvo.getIconId();
			File file = new File(filePath);	//���� ����
			if(file.exists() && !uvo.getIconId().equals("default.png")) {				//������ ������ ���� 
				file.delete();
			}
		}
		
		if (userDAO.uploadImg(userVO)) {
			System.out.println("���ε� ����");
			uvo.setIconId(userVO.getIconId());
			HttpSession session = request.getSession();
			session.setAttribute("user", uvo);
			forward.setRedirect(false);
		}
		else {
			System.out.println("���ε� ����");
			//���ε� ���н� ���� ����
			filePath += "/" + userVO.getIconId();	//���
			File file = new File(filePath);			//���� ����
			if(file.exists()) {						//������ ������ ��
				file.delete();
			}
			forward.setRedirect(false);
		}
		return forward;
	}
}
