//package dai.mail;//package dai.mail;
////
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.mail.MailSender;
////import org.springframework.mail.SimpleMailMessage;
////import org.springframework.stereotype.Service;
////
/////**
//// * @author Crunchify.com
//// *
//// */
////
////@Service("daiEmail")
////public class EmailAPI {
////
////    @Autowired
////    private MailSender mailSender; // MailSender interface defines a strategy
////    // for sending simple mails
////
////    public void crunchifyReadyToSendEmail(String toAddress, String fromAddress, String subject, String msgBody) {
////
////        SimpleMailMessage crunchifyMsg = new SimpleMailMessage();
////        crunchifyMsg.setFrom(fromAddress);
////        crunchifyMsg.setTo(toAddress);
////        crunchifyMsg.setSubject(subject);
////        crunchifyMsg.setText(msgBody);
////        mailSender.send(crunchifyMsg);
////    }
////}
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//import java.util.Properties;
//
//@Configuration
//public class MailConfig {
//
//    @Value("${email.host}")
//    private String host;
//
//    @Value("${email.port}")
//    private Integer port;
//
//    @Value("${email.username}")
//    private String username;
//
//    @Value("${email.password}")
//    private String password;
//
//    @Bean
//    public JavaMailSender javaMailService() {
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//
//        javaMailSender.setHost(host);
//        javaMailSender.setPort(port);
//        javaMailSender.setUsername(username);
//        javaMailSender.setPassword(password);
//
//        javaMailSender.setJavaMailProperties(getMailProperties());
//
//        return javaMailSender;
//    }
//
//    private Properties getMailProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("mail.transport.protocol", "smtp");
//        properties.setProperty("mail.smtp.auth", "false");
//        properties.setProperty("mail.smtp.starttls.enable", "false");
//        properties.setProperty("mail.debug", "false");
//        return properties;
//    }
//}