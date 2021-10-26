package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;

import controller.common.Action;
import controller.common.ActionForward;
import mail.SendEmail;

public class SendCheckEmail implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		SendEmail sendEmail = new SendEmail();

		String email = request.getParameter("email");
		System.out.println("email : "+ email);

		String checkNum = RandomStringUtils.randomNumeric(6);

		sendEmail.sendEmail(email, checkNum);
		HttpSession session = request.getSession();


		session.setAttribute("checkNum", checkNum);




		return null;
	}

}
