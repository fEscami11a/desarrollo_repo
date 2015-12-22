package mx.com.invex.msi.mail;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component("emailSender")
public class EmailSender {

	private static final Logger logger = Logger.getLogger(EmailSender.class);
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    

    public void sendEmail(final String toEmailAddresses, final String fromEmailAddress,
                          final String subject, Map<String,Object> model, String cpc) {
        sendEmail(toEmailAddresses, fromEmailAddress, subject, null,null, model,cpc);
    }

//    public void sendEmailWithAttachment(final String toEmailAddresses, final String fromEmailAddress,
//                                        final String subject, final String attachmentPath,
//                                        final String attachmentName,Map model) {
//        sendEmail(toEmailAddresses, fromEmailAddress, subject, attachmentPath, attachmentName,model);
//    }

    private void sendEmail(final String toEmailAddresses, final String fromEmailAddress,
                           final String subject, final String attachmentPath,
                           final String attachmentName, final Map<String,Object> model,final String cpc) {
    	
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
                message.setTo(toEmailAddresses);
                message.setFrom(new InternetAddress(fromEmailAddress));
                message.setSubject(subject);
                String template= null;
                if("VL2".equals(cpc)){
                	template="mailVolaris2.vm";
                }else if("VL1".equals(cpc)){
                	template="msiVolaris.vm";
                }else{
                	template="msiSiCard.vm";
                }
                String body = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, template, "UTF-8", model);
                logger.info("Mail: "+body);
                message.setText(body, true);
                if (!StringUtils.isBlank(attachmentPath)) {
                    FileSystemResource file = new FileSystemResource(attachmentPath);
                    message.addAttachment(attachmentName, file);
                }
              
            }
        };
        this.mailSender.send(preparator);
        
        
    }
}
