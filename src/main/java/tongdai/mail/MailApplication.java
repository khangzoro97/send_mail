package tongdai.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.MessagingException;

@SpringBootApplication
public class MailApplication implements CommandLineRunner
{
	@Autowired
	private EmailService emailService;

	public static void main(String[] args) {
		LoadConfig config = new LoadConfig();
		config.start();
		SpringApplication.run(MailApplication.class, args);
	}


	@Override
	public void run(String... args) throws MessagingException {
		emailService.sendMailWithAttachment();
		emailService.start();
	}
}
