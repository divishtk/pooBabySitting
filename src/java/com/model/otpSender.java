/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/**
 *
 * @author Nevets
 */
import java.util.Properties;
import java.util.Random;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class otpSender {

    public static int sendMail(String recepient) {

        System.out.println("preparing to send email");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
//    properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.port", "587");

        String MyEmail = "sysattendance@gmail.com";
        String Pass = "Admin@123";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MyEmail, Pass);
            }
        });

        int ROTP;
        Random random = new Random();
        ROTP = random.nextInt(99999);
        Message message = prepareMessage(session, MyEmail, recepient, ROTP);

        try {
            Transport.send(message);
            System.out.println("Message sent successfully!");
        } catch (MessagingException ex) {
//            Logger.getLogger(otpSender.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return ROTP;
    }

    private static Message prepareMessage(Session session, String MyEmail, String recepient, int ROTP) {
        try {
            System.out.println("\n OTP Sent is " + ROTP);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MyEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("OTP for Verification");
            message.setText("\n Copy paste the OTP " + ROTP);

            return message;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
//            Logger.getLogger(otpSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
