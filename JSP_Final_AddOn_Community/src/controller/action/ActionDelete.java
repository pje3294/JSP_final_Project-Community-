package controller.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.board.BoardDAO;
import model.board.BoardVO;

public class ActionDelete implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		response.setContentType("text/html; charset=UTF-8");
		BoardDAO boardDAO = new BoardDAO();
		BoardVO board = new BoardVO();
		
		
		int bId = Integer.parseInt(request.getParameter("bId"));
		String bCtgr = request.getParameter("bCtgr");
		board.setbId(bId);
		
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String path= "board.do";
		if(bCtgr.equals("question")) {
			path = "question.do";
		}
		
		if(boardDAO.delete(board)) {
			out.println("<script>alert('삭제가 완료되었습니다!');location.href='"+path+"'</script>");
			
		}else {
			out.println("<script>alert('삭제가 실패했습니다!');location.href='"+path+"'</script>");
		}
		
		
		
		return null;
	}
	

}
