package springLoop.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DisconnectControllerServlet {
    @Resource
    private HttpSession session;

    @GetMapping("/disconnectController-servlet")
    public String doGet(){
        session.removeAttribute("user");
        session.removeAttribute("type");

        return "redirect:/redirection-servlet";
    }
}
