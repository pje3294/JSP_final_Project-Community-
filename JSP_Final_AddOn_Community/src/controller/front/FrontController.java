package controller.front;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.action.ActionBoard;
import controller.action.ActionDelete;
import controller.action.ActionDeleteReply;
import controller.action.ActionDeleteTest;
import controller.action.ActionDeleteTestReply;
import controller.action.ActionDeleteUser;
import controller.action.ActionDetail;
import controller.action.ActionDetailTest;
import controller.action.ActionFindPw;
import controller.action.ActionForm;
import controller.action.ActionFormTest;
import controller.action.ActionIdCheck;
import controller.action.ActionImgDelete;
import controller.action.ActionImgUpload;
import controller.action.ActionInquery;
import controller.action.ActionInsertReply;
import controller.action.ActionInsertTestReply;
import controller.action.ActionJoin;
import controller.action.ActionLogin;
import controller.action.ActionLogout;
import controller.action.ActionMyPage;
import controller.action.ActionQuestion;
import controller.action.ActionRating;
import controller.action.ActionTest;
import controller.action.ActionUpdate;
import controller.action.ActionUpdateReply;
import controller.action.ActionUpdateTest;
import controller.action.ActionUpdateTestReply;
import controller.action.ActionUpdateUser;
import controller.action.CheckEmail;
import controller.action.FindReply;
import controller.action.FindTestReply;
import controller.action.SendCheckEmail;
import controller.common.ActionForward;

/**
 * Servlet implementation class FrontController
 */
@WebServlet(urlPatterns = {"*.do"})
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**	
	 * @see HttpServlet#HttpServlet()
	 */
	public FrontController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doAction(request, response);
	}

	private void doAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action;
		action = request.getRequestURI();
		int contextPathLength = request.getContextPath().length();
		ActionForward forward = null;
		action = action.substring(contextPathLength);

		// System.out.println("action :"+ action);
		System.out.println(getInitParameter("managerId"));
		if (action.equals("/question.do")) {
			forward = new ActionQuestion().execute(request, response);
		} else if (action.equals("/board.do")) {
			// System.out.println("board.do 실행");
			forward = new ActionBoard().execute(request, response);
		} else if (action.equals("/question.do")) {
			forward = new ActionQuestion().execute(request, response);
		} else if (action.equals("/detail.do")) {
			forward = new ActionDetail().execute(request, response);
		} else if (action.equals("/detailTest.do")) {
			forward = new ActionDetailTest().execute(request, response);
		} else if (action.equals("/test.do")) {
			forward = new ActionTest().execute(request, response);
		} else if (action.equals("/login.do")) {
			System.out.println("login.do 실행중!");
			forward = new ActionLogin().execute(request, response);
		} else if (action.equals("/form.do")) {
			forward = new ActionForm().execute(request, response);
		} else if (action.equals("/formTest.do")) {
			forward = new ActionFormTest().execute(request, response);
		} else if (action.equals("/update.do")) {
			forward = new ActionUpdate().execute(request, response);
		} else if (action.equals("/updateTest.do")) {
			forward = new ActionUpdateTest().execute(request, response);
		} else if (action.equals("/deleteTest.do")) {
			forward = new ActionDeleteTest().execute(request, response);
		} else if (action.equals("/delete.do")) {
			forward = new ActionDelete().execute(request, response);
		} else if (action.equals("/insertReply.do")) {
			System.out.println("insertReply.do");
			forward = new ActionInsertReply().execute(request, response);
		} else if (action.equals("/updateReply.do")) {
			System.out.println("/updateReply.do 실행 중 ");
			forward = new ActionUpdateReply().execute(request, response);
		} else if (action.equals("/deleteReply.do")) {
			forward = new ActionDeleteReply().execute(request, response);
		} else if (action.equals("/myPage.do")) {
			forward = new ActionMyPage().execute(request, response);
			System.out.println("mypage.do if 문 ");
		} else if (action.equals("/insertTestReply.do")) {
			forward = new ActionInsertTestReply().execute(request, response);
		} else if (action.equals("/updateTestReply.do")) {
			forward = new ActionUpdateTestReply().execute(request, response);
		} else if (action.equals("/deleteTestReply.do")) {
			forward = new ActionDeleteTestReply().execute(request, response);
		} else if (action.equals("/join.do")) {
			forward = new ActionJoin().execute(request, response);
		} else if (action.equals("/updateUser.do")) {
			forward = new ActionUpdateUser().execute(request, response);
		} else if (action.equals("/deleteUser.do")) {
			forward = new ActionDeleteUser().execute(request, response);
		} else if (action.equals("/idCheck.do")) {
			forward = new ActionIdCheck().execute(request, response);
		} else if (action.equals("/rating.do")) {
			forward = new ActionRating().execute(request, response);
		} else if (action.equals("/imgUpload.do")) {
			forward = new ActionImgUpload().execute(request, response);
		} else if (action.equals("/imgDelete.do")) {
			// System.out.println(request.getParameter("userNum"));
			forward = new ActionImgDelete().execute(request, response);
		} else if (action.equals("/inquery.do")) {
			forward = new ActionInquery().execute(request, response);
		} else if (action.equals("/findPw.do")) {
			forward = new ActionFindPw().execute(request, response);
		} else if (action.equals("/sendCheckEmail.do")) {
			forward = new SendCheckEmail().execute(request, response);
		} else if (action.equals("/check.do")) {
			forward = new CheckEmail().execute(request, response);
		} else if (action.equals("/findReply.do")) {
			forward = new FindReply().execute(request, response);
		} else if (action.equals("/findTestReply.do")) {
			forward = new FindTestReply().execute(request, response);
		} else if (action.equals("/logout.do")) {
			forward = new ActionLogout().execute(request, response);
		} else {
			forward=new ActionForward();
			forward.setRedirect(false);
			forward.setPath("error.jsp");
		}

		if (forward == null) {
			return;
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
		dispatcher.forward(request, response);

	}

}
