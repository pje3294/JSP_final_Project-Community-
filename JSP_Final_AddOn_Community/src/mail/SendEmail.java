package mail;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

	public boolean sendInquery(String inqueryText) {
		String host = "smtp.naver.com";
		final String user = "개인정보";
		final String password = "개인정보";
		boolean result = false;
		String to = "개인정보@naver.com";

		// Get the session object
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		// Compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("개인정보@naver.com", "Add-on"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Subject
			message.setSubject("[Subject] Java Mail Test");

			// Text
			message.setText(inqueryText);

			// send the message
			Transport.send(message);
			result = true;
			System.out.println("message sent successfully...");

		} catch (MessagingException e) {
			e.printStackTrace();
			result = false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;

	}

	public boolean sendEmail(String email, String text) {
		String host = "smtp.naver.com";
		final String user = "개인정보";
		final String password = "개인정보";
		boolean result = false;
		String to = email;

		// Get the session object
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		// Compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("개인정보@naver.com", "Add-on"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Subject
			message.setSubject("[Subject] Java Mail Test");

			// Text
			message.setText(text);

			// send the message
			Transport.send(message);
			result = true;
			System.out.println("message sent successfully...");

		} catch (MessagingException e) {
			e.printStackTrace();
			result = false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;

	}

	public boolean sendTempPw(String email, String tempPw) {
		String host = "smtp.naver.com";
		final String user = "개인정보";
		final String password = "개인정보";
		boolean result = false;
		String to = email;

		// Get the session object
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		// Compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("개인정보@naver.com", "Add-on")); // msg.setFrom(new InternetAddress(mymail,
																				// "관리자"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Subject
			message.setSubject("[Subject] Java Mail Test");

			// Text

			message.setText("임시 비밀번호 발송 : " + tempPw);

			// send the message
			Transport.send(message);
			result = true;
			System.out.println("message sent successfully...");

		} catch (MessagingException e) {
			e.printStackTrace();
			result = false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;

	}

	public void sendEmails(ArrayList<String> emails, String bTitle, String bContent) {
		String host = "smtp.naver.com";
		final String user = "개인정보";
		final String password = "개인정보";

		// Get the session object
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		// Compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("개인정보@naver.com", "Add-on"));

			int arraySize = emails.size();

			InternetAddress[] addArray = new InternetAddress[arraySize];

			for (int i = 0; i < arraySize; i++) {
				addArray[i] = new InternetAddress(emails.get(i));
			}

//			addArray[0] = new InternetAddress("개인정보@naver.com");
//			addArray[1] = new InternetAddress("개인정보@naver.com");

			message.addRecipients(Message.RecipientType.TO, addArray);

			// Subject
			message.setSubject(bTitle);

			// Text
			message.setText(bContent);

			// send the message
			Transport.send(message);
			System.out.println("message sent successfully...");

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

}
