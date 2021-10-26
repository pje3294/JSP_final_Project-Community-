package controller.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.users.UsersDAO;
import model.users.UsersVO;

public class ActionIdCheck implements Action{

	   @Override
	   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {

	      String id = request.getParameter("id");
	      UsersDAO userDAO=new UsersDAO();
	      UsersVO userVO=new UsersVO();
	      userVO.setId(id);
	      
	      PrintWriter out =null;
	      
	      try {
	         out =response.getWriter();
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      
	      if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,16}$", id)) {
	    	  out.print("false");
	      }
	      else if (userDAO.getDBData(userVO)!=null) {
	    	  out.print("fail");
	      } else {
	    	  out.print("success");
	      }

	      return null;
	   }
	   
	   

}
