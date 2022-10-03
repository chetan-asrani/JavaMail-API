/**
 * 
 */
package javamailapi;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class JavaMailApplication {

	/*Email can be sent to multiple email addresses that's why 
	  toAddresses attribute in EmailDetails class is a List of String*/
	public static void main(String[] args) {
		
		//----------------------------------Step-0 Initializing the Email Details-------------------------------------------
		// Replace the sender and recipients email address
		EmailDetails emailDetails = new EmailDetails("smtp.gmail.com", "465", 
				"Congratualations Mail", "sender@gmail.com", 
				Arrays.asList("recipient@gmail.com"));
		
		Map<String, String> valuesToBeEmbeddedInHtml = new HashMap<>();
		
		//Value corresponding to this name key will embedded inside .ftl template
		//You can add more dynamic attributes in HTML if you want
		valuesToBeEmbeddedInHtml.put("name", "John");  
		
		sendEmailWithHtmlAttachment(emailDetails, valuesToBeEmbeddedInHtml);
	}

	private static void sendEmailWithHtmlAttachment(EmailDetails emailDetails, Map<String, String> valuesToBeEmbeddedInHtml) {
		
		//--------------------------------Step-1 Creating the Java Mail API Session Object---------------------------------------
		
		/*Initializing the Configuration class object below, 
		  to setup the configuration settings for using Apache FreeMarker template*/
		Configuration config = new Configuration(Configuration.VERSION_2_3_23);
		config.setClassForTemplateLoading(JavaMailApplication.class, "/");
		config.setDefaultEncoding("UTF-8");
		config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp"); //Email protocol to be used 
		props.put("mail.smtp.host", emailDetails.getHostName()); //Host name of the SMTP Service provider in this case Gmail
		props.put("mail.smtp.port", emailDetails.getPortNumber()); //Port number of SMTP server
		props.put("mail.smtp.ssl.enable", "true"); //Flag for enabling SSL
		props.put("mail.smtp.auth", "true"); //Flag for enabling authentication
		
		/*Initializing the Session object with fromAddress credentials*/
		Session session=Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				//Replace the 16 character key with teh key for your gmail account
				return new PasswordAuthentication(emailDetails.getFromAddress(), "****************"); 
			}
		});
		
		session.setDebug(true);
		
		//---------------------------------------Step-2 Composing the Email Message---------------------------------------------
		
		try {
			MimeMessage mimeMessage = new MimeMessage(session);
			
			mimeMessage.setSubject(emailDetails.getSubject());//Setting email subject
			
			mimeMessage.setFrom(new InternetAddress(emailDetails.getFromAddress())); //Setting the email address of the sender
			
			Address[] toAddresses = emailDetails.getToAddresses().stream()
									.map(email->{
										try {
											return new InternetAddress(email.trim());
										} catch (AddressException e) {
											e.printStackTrace();
											return null;
										}
									}).toArray(Address[]::new);
			mimeMessage.addRecipients(Message.RecipientType.TO, toAddresses); //Setting the email addresses of the Recipients
			
			
			MimeMultipart mimeMultipart = new MimeMultipart("related");
			
			MimeBodyPart htmlTemplateBodyPart = new MimeBodyPart();
			Template template = config.getTemplate("EmailTemplate.ftl"); //Setting the html template to be used
			String htmlTemplateString = FreeMarkerTemplateUtils.
					                    processTemplateIntoString(template, valuesToBeEmbeddedInHtml); //Converting html template to String
			htmlTemplateBodyPart.setContent(htmlTemplateString, "text/html");
			mimeMultipart.addBodyPart(htmlTemplateBodyPart);//Adding html as the body of the email
			
			/*File path of the attachment
			 *Note: Attachments can be created dynamically for example JSON, XML etc. It depends upon the use case.
			 *In this article I will be attaching a sample pdf file which is present under resources folder of my project*/
			String filePath = "C:\\Users\\<ProfileName>\\Desktop\\Personal Projects\\HTML Email Template for Article\\Java Email Maven Program\\src\\main\\resources\\sample.pdf";
			MimeBodyPart fileAsAttachemntBodyPart = new MimeBodyPart();
			File fileObject = new File(filePath);
			fileAsAttachemntBodyPart.attachFile(fileObject); //Attaching the file to the mail body
			mimeMultipart.addBodyPart(fileAsAttachemntBodyPart);
			

			mimeMessage.setContent(mimeMultipart);
		
		//--------------------------------------------------Step-3 Sending the Email---------------------------------------------------
			
			 Transport.send(mimeMessage); //Sending the mail
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
