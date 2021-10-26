package controller.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.board.BoardDAO;
import model.board.BoardVO;

public class ActionUpdate implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=UTF-8");
		ActionForward forward = null;
		
		BoardDAO boardDAO= new BoardDAO();
		BoardVO board = new BoardVO();
		
		String bCtgr = request.getParameter("bCtgr");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		String bLang = request.getParameter("bLang");
		int bId = Integer.parseInt(request.getParameter("bId"));
		
		board.setbCtgr(bCtgr);
		board.setbTitle(bTitle);
		board.setbContent(bContent);
		board.setbLang(bLang);
		board.setbId(bId);
		
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(boardDAO.update(board)) {
			out.println("<script>alert('글수정이 완료되었습니다!');location.href='detail.do?bId="+bId+"'</script>");
			
		}else {
			out.println("<script>alert('글수정이 실패했습니다!');location.href='detail.do?bId="+bId+"'</script>");

		}		
		
		
		return forward;
	}

}
