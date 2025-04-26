package kraine.app.eq_inventory.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kraine.app.eq_inventory.model.User;

@Service
public class EmailService implements EmailServiceInterface {

    @Autowired
    private JavaMailSender javaMailSender;



    @Override
    public String sendEmail(EmailModel emailModel) {

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            //Email deteails
            simpleMailMessage.setFrom("do-not-reply@eq-inventory.app");
            simpleMailMessage.setTo(emailModel.getRecipient());
            simpleMailMessage.setText(emailModel.getMessageBody());
            simpleMailMessage.setSubject(emailModel.getSubject());

            //Send email
            javaMailSender.send(simpleMailMessage);
            return "Email sent successfully.";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Error sending email";
        }
    }



    public void sendEmailWithLink(String to, String subject) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);

            // HTML content with clickable link
            String htmlContent = "<html><body>"
                    + "<p>Please click the following link:</p>"
                    + "<a href=\"https://www.example.com\">Click here</a>"
                    + "</body></html>";

            helper.setText(htmlContent, true); // true indicates HTML

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }



    public void sendPassword(EmailModel emailModel, String password)
            throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(emailModel.getRecipient());
        helper.setSubject("Equipment Inventory App");
        helper.setFrom("mail@equipment-inventory.app");

        String htmlContent = "<html>" +
                "<body>" +
                "    <h2>Account Created</h2>" +
                "    <p>Your temporary password is :</p>" +
                "    <p>" +
                "        <span style=\"" +
                "            display: inline-block; padding: 10px 20px; font-size: 20px; letter-spacing:1px; font-weight:bold; background-color: #ECB365; " +
                "            color: #041C32; text-decoration: none; border-radius: 5px;" +
                "        \">" + password + "</span>" +
                "    </p>" +
                "<p style=\"color: #041C32; font-weight:bold;\">The password above must be changed after logging in.</p>"+
                "</body>" +
                "</html>";

        helper.setText(htmlContent, true);

        javaMailSender.send(message);
    }
}
