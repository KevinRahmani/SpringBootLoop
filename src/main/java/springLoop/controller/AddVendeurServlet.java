package springLoop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springLoop.user.model.VendeurEntity;
import springLoop.user.service.VendeurService;
import springLoop.utils.ProcessTypeUserServlet;
import springLoop.utils.VerifyData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Timestamp;

@Controller
public class AddVendeurServlet {

    @Autowired
    private VendeurService vendeurService;
    @Autowired
    private ProcessTypeUserServlet processTypeUserServlet;
    @Autowired
    private VerifyData verifyData;

    @GetMapping("/addVendeur-servlet")
    public String doGet(Model model){
        return processTypeUserServlet.processUserType(model);
    }


    @PostMapping("/addVendeur-servlet")
    public String doPost(
            @RequestParam(name = "nom") String nom,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "adresse") String adresse,
            @RequestParam(name = "mail") String mail, //Must end with @loop.com
            Model model
    ){
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        if (VerifyData.verifyParameters(nom, password, adresse, mail, "testSake", "testSake")) {
            if(mail.endsWith("@loop.com") && verifyData.isFreeMailVendeur(mail, null)){
                VendeurEntity vendeur = new VendeurEntity();

                vendeur.setNom(nom);vendeur.setPassword(hashedPassword);vendeur.setMail(mail);
                vendeur.setAdresse(adresse);vendeur.setDatesignup(new Timestamp(System.currentTimeMillis()));
                vendeur.setNbtotalsales(0);vendeur.setAddRight(1);vendeur.setModifyRight(1);vendeur.setRemoveRight(1);

                vendeurService.save(vendeur);

            } else {
                model.addAttribute("errAddVendeur", "Veuillez mettre le nom de domaine à @loop.com");
            }
        } else {
            model.addAttribute("errAddVendeur", "Veuillez remplir correctement les champs");
        }

        return processTypeUserServlet.processUserType(model);
    }


}
