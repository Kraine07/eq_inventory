package kraine.app.eq_inventory.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import kraine.app.eq_inventory.SessionHandler;

@Controller
public class EmailController {


    @Autowired
    private EmailService emailService;

    public EmailController(EmailService es) {
        this.emailService = es;
    }





    // Sending Email
    @GetMapping("/send-password")
    public String sendPassword(HttpServletRequest request){

        try {
            emailService.sendPassword(SessionHandler.getAttribute(request, "recipient", String.class), SessionHandler.getAttribute(request,"rawPass",String.class));
        } catch (MessagingException e) {
            System.out.println("Error sending email...");
            e.printStackTrace();
        }
        return "redirect:";
    }




}
