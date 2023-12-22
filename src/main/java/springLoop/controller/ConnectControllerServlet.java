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
import springLoop.utils.VerifyData;

@Controller
public class ConnectControllerServlet {

    @Autowired
    private ClientService clientService;
    @Autowired
    private VendeurService vendeurService;
    @Autowired
    private AdminService adminService;
    @Resource
    private HttpSession session;

    @GetMapping("/connectController-servlet")
    public String doGet(){
        return "/WEB-INF/connexion.jsp";
    }

    @PostMapping("/connectController-servlet")
    public String doPost(@RequestParam(name = "name") String mail, @RequestParam(name = "password") String password,
                         Model model) {
        if (password != null && mail != null && !password.isEmpty() && !mail.isEmpty()) {
            if (VerifyData.isVendeurEmail(mail)) {
                VendeurEntity vendeur = vendeurService.connect(mail, password);
                if (vendeur != null && BCrypt.checkpw(password, vendeur.getPassword())) {
                    session.setAttribute("user",vendeur);
                    session.setAttribute("type", "vendeur");
                    return "redirect:/redirection-servlet";
                }
            } else if(VerifyData.isAdminEmail(mail)){
                AdminEntity admin = adminService.connect(mail, password);
                if(admin != null && BCrypt.checkpw(password, admin.getPassword())){
                    session.setAttribute("user",admin);
                    session.setAttribute("type", "admin");
                    return "redirect:/redirection-servlet";
                }
            } else {
                ClientEntity client = clientService.connect(mail, password);
                if (client != null && BCrypt.checkpw(password, client.getPassword())) {
                    session.setAttribute("user", client);
                    session.setAttribute("type", "client");
                    return "redirect:/redirection-servlet";
                }
            }
        }
        model.addAttribute("err", 1);
        return "/WEB-INF/connexion.jsp";
    }

}
