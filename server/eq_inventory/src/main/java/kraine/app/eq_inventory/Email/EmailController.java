package kraine.app.eq_inventory.Email;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kraine.app.eq_inventory.model.User;

@Controller
public class EmailController {


    private EmailService emailService;

    public EmailController(EmailService es) {
        this.emailService = es;
    }



    public static String getPassword(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
        Object userObj = session.getAttribute("pgen");
        if (userObj instanceof String) {
            return (String) userObj;
        }
    }
    return null;
}

    // Sending Email
    @PostMapping("/send-password")
    public String sendPassword(@ModelAttribute EmailModel emailModel, HttpServletRequest request){

        try {
            emailService.sendPassword(emailModel, EmailController.getPassword(request));
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "redirect:/";
    }




}
