package util;
import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.ParseException;

import configuration.Settings;

public class Mailer {
	//private String SMTP_HOST ="smtp.mail.yahoo.com";
	Settings set = Settings.getInstance();
	private String SMTP_HOST = set.getSmtpUrl();
	
	private String SOCKET_FACTORY_PORT = set.getSmtpPort();
	private String SOCKET_FACTORY_CLASS = "javax.net.ssl.SSLSocketFactory";
	private String SMTP_AUTH="true";
	private String SMTP_PORT = set.getSmtpPort();
	//private String USER_NAME ="freshgravityapi@yahoo.com";
	//private String PASSWORD = "fresh#gravity12";
	private String USER_NAME =set.getSmtpUsername();
	private String PASSWORD =  set.getSmtpPassword();
		
	public boolean sendNotification(String receiverList[],
			String notificationSubject,
			String notificationBody,
			String attachmentFileLocationList[])
	{
		  Properties messageProperties = new Properties();  
		  messageProperties.put("mail.smtp.host", SMTP_HOST);  
		  messageProperties.put("mail.smtp.socketFactory.port", SOCKET_FACTORY_PORT);  
		  messageProperties.put("mail.smtp.socketFactory.class",SOCKET_FACTORY_CLASS);  
		  messageProperties.put("mail.smtp.auth", SMTP_AUTH);  
		  messageProperties.put("mail.smtp.port", SMTP_PORT);
		  
		  //Session Created
		  Session session = Session.getDefaultInstance(messageProperties, new javax.mail.Authenticator() {  
				   protected PasswordAuthentication getPasswordAuthentication() {  
				   return new PasswordAuthentication(USER_NAME,PASSWORD);}});
		  try{
		  try{
			  MimeMessage message = new MimeMessage(session);
			  message.setFrom(new InternetAddress(USER_NAME));
			  for(String receiver:receiverList)
			  {
				  message.addRecipient(Message.RecipientType.TO,new InternetAddress(receiver));  
				  
			  }
			  message.setSubject(notificationSubject);  
			   
			   //message.setText(text);
 
			   BodyPart notificationBodyPart = new MimeBodyPart();  
			   notificationBodyPart.setText(notificationBody);  
			   
			   
			   Multipart multipart = new MimeMultipart();  
			   multipart.addBodyPart(notificationBodyPart); 
				    
			   for(String attachement:attachmentFileLocationList)
			   {
				   MimeBodyPart attachmentPart = new MimeBodyPart();  
				   DataSource source = new FileDataSource(attachement);  
				   attachmentPart.setDataHandler(new DataHandler(source));  
				   attachmentPart.setFileName(new File(attachement).getName());
				   multipart.addBodyPart(attachmentPart);
			   }

			   //6) set the multiplart object to the message object  
			    message.setContent(multipart );  
			   
			   
			   Transport.send(message);  
			   return true;
		  }
		  
		  catch(AddressException e){e.printStackTrace();
		  }
		  
		  catch (MessagingException e) {e.printStackTrace();
		  
		  }
		  
		  catch(Exception e){e.printStackTrace();
		  }
		  }
		  catch(Exception e)
		  {
			  
		  }
		  return true;
	}
	
	
	/*
	 * Function to send mail
	 */
	public boolean sendMail(String receiver,String subject,String text)
	{
		  Properties messageProperties = new Properties();  
		  messageProperties.put("mail.smtp.host", SMTP_HOST);  
		  messageProperties.put("mail.smtp.socketFactory.port", SOCKET_FACTORY_PORT);  
		  messageProperties.put("mail.smtp.socketFactory.class",SOCKET_FACTORY_CLASS);  
		  messageProperties.put("mail.smtp.auth", SMTP_AUTH);  
		  messageProperties.put("mail.smtp.port", SMTP_PORT);
		  
		  //Session Created
		  Session session = Session.getDefaultInstance(messageProperties, new javax.mail.Authenticator() {  
				   protected PasswordAuthentication getPasswordAuthentication() {  
				   return new PasswordAuthentication(USER_NAME,PASSWORD);}});
		  try{
		  try{
			  MimeMessage message = new MimeMessage(session);
			  message.setFrom(new InternetAddress(USER_NAME));
			   message.addRecipient(Message.RecipientType.TO,new InternetAddress(receiver));  
			   
			   message.setSubject(subject);  
			   
			   //message.setText(text);
			   
			   BodyPart messageBodyPart1 = new MimeBodyPart();  
			    messageBodyPart1.setText("This is message body");  
			      
			    //4) create new MimeBodyPart object and set DataHandler object to this object      
			    MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
			    MimeBodyPart messageBodyPart3 = new MimeBodyPart();  
				  
			    String filename = "./files/settings.xml";//change accordingly  
			    DataSource source = new FileDataSource(filename);  
			    messageBodyPart2.setDataHandler(new DataHandler(source));  
			    messageBodyPart2.setFileName(new File(filename).getName());  
			    
			    String filename2 = "./files/mapping.csv";//change accordingly  
			    DataSource source2 = new FileDataSource(filename2);  
			    messageBodyPart3.setDataHandler(new DataHandler(source2));  
			    messageBodyPart3.setFileName(filename2);  
			     
			     
			    //5) create Multipart object and add MimeBodyPart objects to this object      
			    Multipart multipart = new MimeMultipart();  
			    multipart.addBodyPart(messageBodyPart1);  
			    multipart.addBodyPart(messageBodyPart2);  
			    multipart.addBodyPart(messageBodyPart3);
			    
			    
			    //6) set the multiplart object to the message object  
			    message.setContent(multipart );  
			   
			   
			   Transport.send(message);  
			   return true;
		  }
		  
		  catch(AddressException e){e.printStackTrace();
			  //throw new AllExceptions(ErrorCode.ADDRESS_EXCEPTION);
		  }
		  
		  catch (MessagingException e) {e.printStackTrace();
		  //throw new AllExceptions(ErrorCode.MESSAGING_EXCEPTION);
		  }
		  
		  catch(Exception e){e.printStackTrace();
		  //throw new AllExceptions(ErrorCode.EXCEPTION);
		  }
		  }
		  catch(Exception e)
		  {
			  
		  }
		  return true;
		
	}
	
	
}
