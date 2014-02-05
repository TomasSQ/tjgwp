package br.com.tjgwp.business.service.email;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.business.service.SuperService;

public class EmailService extends SuperService {

	public void sendWelcomeMessage(UserEntity userEntity) throws UnsupportedEncodingException {
		// ...
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		String msgBody = "...";

		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("tomasqueiroga@gmail.com", "Tomas"));
			msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(userEntity.getEmail(), userEntity.getNickname()));
			msg.setSubject("Your willpoe account has been activated");
			msg.setText(msgBody);
			Transport.send(msg);

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
