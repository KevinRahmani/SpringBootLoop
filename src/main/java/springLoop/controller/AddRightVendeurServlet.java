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

@Controller
public class AddRightVendeurServlet {

    @Autowired
    private VendeurService vendeurService;

    @Autowired
    ProcessTypeUserServlet processTypeUserServlet;

    @GetMapping("/addRighVendeur-servlet")
    public String doGet(Model model){
        return processTypeUserServlet.processUserType(model);
    }

    @PostMapping("/addRightVendeur-servlet")
    public String doPost(
            @RequestParam(name = "vendeur") String idParam,
            @RequestParam(name = "right") String right,
            Model model){

        int idVendeur = VerifyData.getParameterAsInt(idParam);
        if(idVendeur != 0 && right != null && !right.isEmpty()){

            VendeurEntity vendeur = vendeurService.findById(idVendeur);
            switch (right){
                case "addRight":
                    vendeur.setAddRight(1);
                    break;
                case "modifyRight":
                    vendeur.setModifyRight(1);
                    break;
                case "deleteRight":
                    vendeur.setRemoveRight(1);
                    break;
            }
            vendeurService.save(vendeur);
        } else {
            model.addAttribute("errAddRight", "Erreur");
        }
        return doGet(model);
    }
}
