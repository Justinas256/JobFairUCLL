/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.utils;

/**
 *
 * @author GreKar
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import jobFair.model.EmailData;
import jobFair.model.Spot;
import jobFair.model.Users;
import jobFair.service.EmailDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmailSender {
    @Autowired
    private EmailDataService emailDataService;
    
        private String username = "jobfairtesting", password = "uclljobfair";
	private String messageHeader = "<meta charset=\"ISO-8859-1\">";
	private String indexPage = "http://ipajax.cyclone2.khleuven.be:38033/jobfair_group4/";
	private Properties properties;


	public EmailSender() {
            loadProperties();
	}
	
	private void loadProperties() {
            properties = new Properties();
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.password", password);
            properties.put("mail.smtp.user", username);
	}

	public void sendConfirmationMail(Spot spot, String company, String emailreceiver) throws MessagingException {
            String subject = "Jobbeurs 2017 - UCLL Leuven: Bevestiging plaats";
            String message = messageHeader;
            message += "Beste,<br><br>Uw bedrijf, "
                            + company + ", reserveerde de plaats met nummer " + spot.getSpotNo() + ".<br>"
                            + "Het volgende zal voor u voorzien worden:<br><ul><li>"
                            + spot.getChairs() + " stoelen</li>"
                            + "<li>"+spot.getTables()+" tafels</li>";
            if (spot.isElectricity()) {
                    message+="<li>elektriciteit</li>";
            }
            message += "</ul><br>Tot binnenkort.<br><br>"
                            + "--<br>"
                            + "Mvg,<br>"
                            + "Team Scrumbags";
            sendFromGMail(subject, message, emailreceiver);
	}

	public void sendUserLinkedToSpotMail(String spotID, String company, String emailreceiver) throws MessagingException {
		String subject = "Jobbeurs 2017 - UCLL Leuven: Toewijzing plaats";
		String message = messageHeader;
		message += "Beste,<br><br>Uw bedrijf, "
				+ company + ", kreeg de plaats met nummer " + spotID + " toegewezen.<br>"
				+ "Wij voorzien het volgende voor u:<br><ul>"
				+ "<li>2 stoelen</li>"
				+ "<li>1 tafel</li>"
				+ "<li>elektriciteit</li>"
				+ "</ul><br>Tot binnenkort.<br><br>"
				+ "--<br>"
				+ "Mvg,<br>"
				+ "Team Scrumbags";
		sendFromGMail(subject, message, emailreceiver);
	}

	public void sendNewCompanyMail(String userID, String password, String emailreceiver) throws MessagingException {
		String subject = "Jobbeurs 2017 - UCLL Leuven";
		String message = messageHeader;
		message += "Beste,<br><br>We mogen je met veel plezier melden dat je vanaf nu een plaats kunt reserveren voor onze jobbeurs.<br>"
				+ "Inloggen doe je <a href=\""+indexPage+"login.jsp\">hier</a> met volgende login-gegevens:<br>"
				+ "UserID: " + userID + "<br>"
				+ "Wachtwoord: " + password + "<br><br>"
				+ "--<br>"
				+ "Mvg,<br>"
				+ "Team Scrumbags";
		sendFromGMail(subject, message, emailreceiver);
	}

	public void sendNewAdminMail(String userID, String password, String emailreceiver) throws MessagingException {
		String subject = "Beheerder website Jobbeurs - UCLL Leuven";
		String message = messageHeader;
		message += "Beste,<br><br>Vanaf heden heeft u de toestemming gekregen om als beheerder in te loggen op onze website.<br>"
				+ "Bekijk <a href=\""+indexPage+"files/Readme.pdf\">hier</a> de handleiding voor beheerders.<br>"
				+ "Inloggen doe je <a href=\""+indexPage+"login.jsp\">hier</a> met volgende login-gegevens:<br>"
				+ "UserID: " + userID + "<br>"
				+ "Wachtwoord: " + password + "<br><br>"
				+ "--<br>"
				+ "Mvg,<br>"
				+ "Team Scrumbags";
		sendFromGMail(subject, message, emailreceiver);
	}

	public void sendUpdateMail(Spot spot, String company, String emailreceiver) throws MessagingException {
		String subject = "Jobbeurs 2017 - UCLL Leuven: Wijziging plaats";
		String message = messageHeader;
		message += "Beste,<br><br>Uw bedrijf, "
				+ company + ", wijzigde uw vookeuren voor de plaats met nummer " + spot.getSpotNo()+ ".<br>"
				+ "Het volgende zal nu voor u voorzien worden:<br><ul><li>"
				+ spot.getChairs() + " stoelen</li>"
				+ "<li>"+spot.getTables()+" tafels</li>";
		if (spot.isElectricity()) {
			message+="<li>elektriciteit</li>";
		}
		message += "</ul><br>Tot binnenkort.<br><br>"
				+ "--<br>"
				+ "Mvg,<br>"
				+ "Team Scrumbags";
		sendFromGMail(subject, message, emailreceiver);
	}

	public void sendCancelationMail(Spot spot, String company, String emailreceiver) throws MessagingException {
		String subject = "Jobbeurs 2017 - UCLL Leuven: Annulatie plaats";
		String message = messageHeader;
		message += "Beste "+company+",<br><br>Uw plaats met nummer " + spot.getSpotNo() + " werd geannuleerd.<br>"
				+ "Indien u deze mail krijgt zonder op de hoogte te zijn van deze veranderingen, gelieve dan contact op "
				+ "te nemen met onze verantwoordelijke"
				+ "<br>Tot binnenkort.<br><br>"
				+ "--<br>"
				+ "Mvg,<br>"
				+ "Team Scrumbags";
		sendFromGMail(subject, message, emailreceiver);
	}

	public void sendAlmostSoldOutMail(List<String> emailreceivers) throws MessagingException {
		String subject = "Jobbeurs 2017 - UCLL Leuven: Bijna volzet";
		String message = messageHeader;
		message += "Melding voor de administrator: De te huren locaties zijn bijna volzet, er zijn minder dan 10 plaatsen nog vrij.<br>"
				+ "Het is dus ongeveer tijd geworden om meer standplaatsen toe te voegen zodat meer bedrijven "
				+ "kunnen strijden voor een plaatsje.";
		this.sendMultipleFromGMail(subject, message, emailreceivers);
	}

	public void sendEndOfRegistrationMail(Calendar deadline, List<String> emailreceivers, String subject, String endMessage) throws MessagingException {
		String message = messageHeader;
		message += "Beste,<br><br>";
		if (deadline==null) {
			message += "Weldra";
		} else {
			Date date = deadline.getTime();
			message	+= "Vanaf " + new SimpleDateFormat("EEEE").format(date) + " "
				+ new SimpleDateFormat("dd").format(date) + " "
				+ new SimpleDateFormat("MMMM").format(date);
		}
                endMessage = endMessage.replace("\n", "<br>");              
                message += endMessage;
		this.sendMultipleFromGMail(subject, message, emailreceivers);
	}
	
	public void sendResetPasswordMail(Users user, String password, String emailreceiver) throws MessagingException {
		String subject = "Jobbeurs 2017 - UCLL Leuven: Wachtwoord resetten";
		String message = messageHeader;
		message += "Beste " + user.getCompanyName() + ",<br><br>"
						+ "U heeft ons gemeld dat u uw wachtwoord vergeten bent waarmee u kunt in loggen voor de jobbeurs.<br>"
						+ "Om opnieuw gebruik te kunnen maken van onze applicate kan u onderstaand wachtwoord gebruiken met de zelfde gebruikersnaam:<br>"
						+ "UserID: " + user.getUsername()+ "<br>"
						+ "Wachtwoord: " + password + "<br><br>"
						+ "--<br>"
						+ "Mvg,<br>"
						+ "Team Scrumbags";
		this.sendFromGMail(subject, message, emailreceiver);
	}

	public void sendQuestionMail(List<String> to, String from, String subj, String msg) throws MessagingException {
		String subject = "Jobbeurs - Vraag van " + from + " - " + subj;
		String message = messageHeader;
		message += msg + "<br><br>--<br>Deze vraag werd verzonden vanaf de jobbeurs-webapp.<br><br>Team Scrumbags----";
		for(String rec : to){
			this.sendFromGMail(subject, message, rec);
		}
	}

	private void sendFromGMail(String subject, String body, String to) throws MessagingException {
		Session session = Session.getDefaultInstance(properties);
		
		MimeMessage message = new MimeMessage(session);
		InternetAddress toAddress = new InternetAddress(to);
		message.setRecipient(Message.RecipientType.TO, toAddress);
		message.setSubject(subject);
		message.setContent(body, "text/html; charset=utf-8");
		
		String from = properties.getProperty("mail.smtp.user");
		String password = properties.getProperty("mail.smtp.password");
		Transport transport = session.getTransport("smtp");
		transport.connect(from, password);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

	private void sendMultipleFromGMail(String subject, String body, List<String> emailreceivers) throws MessagingException {
		Session session = Session.getDefaultInstance(properties);
		
		String from = properties.getProperty("mail.smtp.user");
		String password = properties.getProperty("mail.smtp.password");
		Transport transport = session.getTransport("smtp");
		transport.connect(from, password);

		for(int i = 0; i < emailreceivers.size(); i++) {
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			message.setContent(body, "text/html; charset=utf-8");

			InternetAddress toAddress = new InternetAddress(emailreceivers.get(i));
			message.setRecipient(Message.RecipientType.TO, toAddress);

			transport.sendMessage(message, message.getAllRecipients());
		}

		transport.close();
	}

	public void sendNewCompanyMail(Map<Users, String> mailList) throws MessagingException {
		String subject = "Jobbeurs 2017 - UCLL Leuven";
		Session session = Session.getDefaultInstance(properties);
		
		String from = properties.getProperty("mail.smtp.user");
		String password = properties.getProperty("mail.smtp.password");
		Transport transport = session.getTransport("smtp");
		transport.connect(from, password);
		
		for(Users user : mailList.keySet()) {
			String body = messageHeader
					+ "Beste,<br><br>We mogen je met veel plezier melden dat je vanaf nu een plaats kunt reserveren voor onze jobbeurs.<br>"
					+ "Inloggen doe je <a href=\""+indexPage+"\">hier</a> met volgende login-gegevens:<br>"
					+ "UserID: " + user.getUsername()+ "<br>"
					+ "Wachtwoord: " + mailList.get(user) + "<br><br>"
					+ "--<br>"
					+ "Mvg,<br>"
					+ "Team Scrumbags";
	
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			message.setContent(body, "text/html; charset=utf-8");
	
			InternetAddress toAddress = new InternetAddress(user.getEmail());
			message.setRecipient(Message.RecipientType.TO, toAddress);
	
			transport.sendMessage(message, message.getAllRecipients());
		}

		transport.close();
	}
}

