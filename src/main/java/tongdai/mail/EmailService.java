package tongdai.mail;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;


@Service("emailService")
public class EmailService extends Thread
{
    @Autowired
    private JavaMailSender mailSender;


    /**
     * This method will send compose and send the message
     * */

    public void sendMailWithAttachment() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        System.out.println(dateFormat.format(date));

        MimeMessagePreparator preparator = mimeMessage -> {

            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            String to_1 = Preference.to_1;
            String to_2 = Preference.to_2;
            String to_3 = Preference.to_3;
            message.setTo(new String[]{to_1,to_2,to_3});
            message.setFrom(new InternetAddress("sale@mobifoneplus.com.vn"));
            String subject = Preference.subject;
            message.setSubject(subject);
            String body = Preference.body;
            message.setText(body);

            String fileToAttach = Preference.fileToAttach;
            FileSystemResource file = new FileSystemResource(new File(fileToAttach));
            message.addAttachment("export_daily_"+dateFormat.format(date)+".txt", file);
        };

        try {
            mailSender.send(preparator);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }
    public void run() {

        try {
            while(true){
                sendMailWithAttachment();
                sleep(86401000);
            }
        } catch (Exception ex3) {
            ex3.printStackTrace();

        }
    }
}
