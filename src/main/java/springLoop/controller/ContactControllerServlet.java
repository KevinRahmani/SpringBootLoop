package springLoop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springLoop.utils.GMailerUtils;
import springLoop.utils.VerifyData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static springLoop.utils.GMailerUtils.TEST_EMAIL;

@Controller
public class ContactControllerServlet {

    @GetMapping("/contactController-servlet")
    public String doGet(){
        return "/WEB-INF/contact.jsp";
    }

    @PostMapping("/contactController-servlet")
    public String doPost(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "mail") String mail,
            @RequestParam(name = "message") String message,
            Model model
    ){
        String status = "Veuillez remplir tous les champs";

        try {
            if(VerifyData.verifyParameters(name,mail,message,"testSake", "testSake", "testSake")){
                status = "Demande de contact envoyé avec succès";
                String subject = "Demande de contact";
                String content = "Bonjour Administrateur,<br><br>Vous avez reçu un nouveau messsage de "
                        + name + " le " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " dont l'adresse mail est : " + mail +
                        " : <br><br>" + message;

                new GMailerUtils().sendMail(subject,content,TEST_EMAIL);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("status", status);
        return "/WEB-INF/contact.jsp";
    }
}
