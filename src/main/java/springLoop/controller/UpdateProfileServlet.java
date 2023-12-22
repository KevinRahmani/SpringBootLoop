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
import springLoop.user.model.AdminEntity;
import springLoop.user.model.ClientEntity;
import springLoop.user.model.VendeurEntity;
import springLoop.user.service.AdminService;
import springLoop.user.service.ClientService;
import springLoop.user.service.VendeurService;
import springLoop.utils.ProcessTypeUserServlet;
import springLoop.utils.VerifyData;

@Controller
public class UpdateProfileServlet {

    @Resource
    private HttpSession session;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private VendeurService vendeurService;

    @Autowired
    private ProcessTypeUserServlet userTypeProcessor;

    @Autowired
    private VerifyData verifyData;

    @GetMapping("/updateProfile-servlet")
    public String doGet(Model model){
        return userTypeProcessor.processUserType(model);
    }

    @PostMapping("/updateProfile-servlet")
    public String doPost(
            @RequestParam(name = "password")String password,
            @RequestParam(name = "mail")String mail,
            @RequestParam(name = "nom")String nom,
            @RequestParam(name = "adresse")String adresse,
            Model model
    ){
        String typeUser = (String) session.getAttribute("type");
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        //verify if parameters not null and not empty
        if(VerifyData.verifyParameters(password,mail,nom,adresse,"testSake","testSake")) {
            switch (typeUser) {
                case "admin": {
                    if (mail.endsWith("@adminloop.com")) {
                        AdminEntity admin = (AdminEntity) session.getAttribute("user");
                        admin.setNom(nom);
                        admin.setPassword(hashedPassword);
                        admin.setMail(mail);
                        admin.setAdresse(adresse);
                        adminService.save(admin);
                        session.setAttribute("user", admin);
                    } else {
                        model.addAttribute("err", "Le nom de domaine doit etre @adminloop.com");
                    }
                    break;
                }

                case "vendeur": {
                    if (mail.endsWith("@loop.com") && verifyData.isFreeMailVendeur(mail, (VendeurEntity) session.getAttribute("user"))) {
                        VendeurEntity vendeur = (VendeurEntity) session.getAttribute("user");
                        vendeur.setNom(nom);
                        vendeur.setPassword(hashedPassword);
                        vendeur.setMail(mail);
                        vendeur.setAdresse(adresse);
                        vendeurService.save(vendeur);
                        session.setAttribute("user", vendeur);
                    } else {
                        model.addAttribute("err", "Le nom de domaine doit etre @loop.com");
                    }
                    break;
                }

                case "client": {
                    if (VerifyData.isValidMail(mail) && verifyData.isFreeMailClient(mail, (ClientEntity) session.getAttribute("user"))){
                        ClientEntity client = (ClientEntity) session.getAttribute("user");
                        client.setNom(nom);
                        client.setPassword(hashedPassword);
                        client.setMail(mail);
                        client.setAdresse(adresse);
                        clientService.save(client);
                        session.setAttribute("user", client);
                    } else {
                        model.addAttribute("err", "Veuillez rentrer une adresse mail valide et non prise");
                    }
                    break;
                }
            }

        } else {
            model.addAttribute("err", "Veuillez remplir tous les champs");
        }

        return "redirect:/updateProfile-servlet";
    }
}
