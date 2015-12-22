package mx.com.invex.msi.util;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;

import mx.com.invex.msi.util.MSIConstants;

public class EnviarMail {

	public EnviarMail() {
		
	}	

	public static boolean send(String toAddress, String ccAddress, String bccAddress,
			String subject, boolean isHTMLFormat, StringBuffer body,
			boolean debug) {

		MimeMultipart multipart = new MimeMultipart("related");

		Properties properties = new Properties();

		properties.put("mail.smtp.host", hostSmtp);
		properties.put("mail.smtp.port", port);
		properties.put("mail.transport.protocol","smtp");
		
		Session session = Session.getDefaultInstance(properties, null);
		session.setDebug(debug);
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(senderAddress, "Promociones MSI"));
			msg.setRecipients(Message.RecipientType.TO, toAddress);
			msg.setRecipients(Message.RecipientType.CC, ccAddress);
			msg.setRecipients(Message.RecipientType.BCC, bccAddress);			
			msg.setSubject(subject);
			msg.setSentDate(new Date());

			MimeBodyPart mbp = new MimeBodyPart();
			if (isHTMLFormat) {
				mbp.setContent(body.toString(), "text/html");
			}
		
			multipart.addBodyPart(mbp);

//			MimeBodyPart header = new MimeBodyPart();
//			FacesContext context = FacesContext.getCurrentInstance();
//			ServletContext request = (ServletContext) context.getExternalContext().getContext();
////			File file = new File(request.getRealPath("/images/headerMSI.jpg"));			
//			if (file.exists()) {				
//				header.attachFile(file.getAbsoluteFile());
//				header.setHeader("Content-ID", "<headerMail>");
//				//multipart.addBodyPart(header);
//			}
//			
//			MimeBodyPart footer = new MimeBodyPart();
//			File file1 = new File(request.getRealPath("/images/footerMSI.jpg"));
//			if (file1.exists()) {
//				footer.attachFile(file1.getAbsolutePath());
//				footer.setHeader("Content-ID", "<footerMail>");				
//				//multipart.addBodyPart(footer);				
//			}      		
//			
//			MimeBodyPart linea = new MimeBodyPart();
//			File fileLine = new File(request.getRealPath("/images/lineaMSI.jpg"));
//			if (fileLine.exists()) {
//				linea.attachFile(fileLine.getAbsolutePath());
//				linea.setHeader("Content-ID", "<lineaMail>");				
//				//multipart.addBodyPart(linea);				
//			}      		
			//cid:lineaMail
			msg.setContent(multipart);
			Transport.send(msg);
			
		} catch (Exception mex) {
			mex.printStackTrace();
			System.out.println(">> MailSender.send() error = " + mex);
			return false;
		}
		return true;
	}

	private static String hostSmtp= MSIConstants.MAIL_SMTP_HOST;
	private static String port= MSIConstants.MAIL_SMTP_PORT;
	private static String senderAddress=MSIConstants.SENDER_ADDRESS;
}
