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
public class DeleteVendeurServlet {

    @Autowired
    private VendeurService vendeurService;
    @Autowired
    private ProcessTypeUserServlet processTypeUserServlet;
    @Autowired
    private VerifyData verifyData;

    @GetMapping("/deleteVendeur-servlet")
    public String doGet(Model model){
        return processTypeUserServlet.processUserType(model);
    }

    @PostMapping("/deleteVendeur-servlet")
    public String doPost(
            @RequestParam(name = "idVendeur") String idParam,
            Model model
    ){
        //Parameters
        int idVendeur = VerifyData.getParameterAsInt(idParam);
        if(idVendeur != 0){

            VendeurEntity vendeur = vendeurService.findById(idVendeur);

            vendeurService.delete(vendeur);

        } else {
            model.addAttribute("errDeleteVendeur", "Veuillez remplir correctement les champs");
        }
        return processTypeUserServlet.processUserType(model);
    }
}
