package springLoop.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springLoop.user.model.ClientEntity;
import springLoop.user.service.ClientService;
import springLoop.utils.VerifyData;
import org.mindrot.jbcrypt.BCrypt;

@Controller
public class RegisterControllerServlet {

    @Resource
    private HttpSession session;

    @Autowired
    private ClientService clientService;

    @Autowired
    private VerifyData verifyData;

    @GetMapping("/registerController-servlet")
    public String doGet(){
        return "/WEB-INF/connexion.jsp";
    }

    @PostMapping("/registerController-servlet")
    public String doPost(
            @RequestParam(name = "nom") String nom,
            @RequestParam(name = "mail") String mail,
            @RequestParam(name = "adresse") String adresse,
            @RequestParam(name="password") String password, Model model
    ){
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        //if parameters not null and not empty
        if (VerifyData.verifyParameters(nom, mail, adresse, password, "testSake", "testSake")) {
            //if email not domain email from seller and admin
            if (!VerifyData.isAdminEmail(mail) && !VerifyData.isVendeurEmail(mail)) {
                //if email valid
                if(VerifyData.isValidMail(mail)) {
                    //verify if mail is already taken
                    if (verifyData.isFreeMailClient(mail, null)) {
                        ClientEntity client = new ClientEntity();
                        client.setUp(nom, hashedPassword, mail, adresse);
                        clientService.save(client);

                        session.setAttribute("user", client);
                        session.setAttribute("type", "client");
                        //sending mail
                        MailControllerServlet.sendMail(session, "inscription", client);

                        return "redirect:/redirection-servlet";
                    } else {
                        model.addAttribute("errRegister", "Adresse mail déjà prise");
                    }
                } else {
                    model.addAttribute("errRegister", "Veuillez rentrer une adresse mail valide");
                }
            } else {
                model.addAttribute("errRegister", "Veuillez choisir un nom de domaine différent");
            }
        } else {
            model.addAttribute("errRegister", "Champs manquants");
        }

        return "/WEB-INF/connexion.jsp";
    }
}
