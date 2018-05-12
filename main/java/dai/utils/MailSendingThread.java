package dai.utils;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class MailSendingThread extends Thread {
    JavaMailSender sender;
    String to;
    String subject;
    String message;

    public MailSendingThread(JavaMailSender sender, String to, String subject, String message) {
        this.sender = sender;
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    @Override
    public void run() {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(to);
            helper.setFrom("oprea.bg@gmail.com");
            helper.setText(this.message);
            helper.setSubject(subject);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        sender.send(message);
        super.run();
    }
}
