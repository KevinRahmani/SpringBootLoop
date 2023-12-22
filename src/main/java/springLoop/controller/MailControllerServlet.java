package springLoop.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springLoop.basket.model.Basket;
import springLoop.product.model.ArticleEntity;
import springLoop.user.model.ClientEntity;
import springLoop.user.service.ClientService;
import springLoop.utils.GMailerUtils;

import java.util.List;
import java.util.Map;

@Controller
public class MailControllerServlet {

    @Autowired
    private ClientService clientService;

    @Resource
    public HttpSession session1;

    public static void sendMail(HttpSession session, String choice, ClientEntity client){

        String emailContent = "", emailSubject = "";

        try {
            switch (choice){
                case "inscription":
                    //Parameters
                    emailSubject = "Bienvenue chez Loop !";
                    emailContent = "<h1>Inscription Loop</h1><br>"+
                            "    <div style=\"background-color: #f4f4f4; padding: 20px; text-align: center;\"><br>" +
                            "        <h2>"+ client.getNom() +", Loop vous souhaite la bienvenue sur notre plateforme !</h2><br>" +
                            "        <p>Merci de vous être inscrit(e). Votre compte a été créé avec succès.</p><br>" +
                            "        <p>Vous pouvez maintenant accéder à notre service en vous connectant avec vos identifiants.</p><br>" +
                            "        <p>Nous vous disons à très vite.</p><br>" +
                            "    </div>";
                    break;

                case "command" :
                    //Parameters
                    Basket basket = (Basket) session.getAttribute("basket");
                    double totalPriceTTC = (Double) session.getAttribute("totalPriceTTC");

                    emailSubject = "Commande Loop";
                    emailContent = "Bonjour "+client.getNom()+",<br><br>"+
                            "Merci pour votre commande d'un montant total de " +totalPriceTTC + " euros.<br>" +
                            "Vous serez livré sous un délai de 3 jours hors week-end et jours fériés au " + client.getAdresse() +".<br>" +
                            "Voici votre commande : <br><br>";
                    for (Map.Entry<ArticleEntity, Integer> entry : basket.getPanier().entrySet()){
                        emailContent+="Article : " +entry.getKey().getNom() + "<br>Quantité : " + entry.getValue() +"<br><br>";
                    }
                    emailContent+="<br>Merci pour votre achat.<br>L'équipe Loop vous dis à bientot !";
                    break;

                case "publicity":
                    //
                    emailSubject = "Promotions Loop !";
                    emailContent ="Bonjour " + client.getNom() +",<br><br>" +
                            "C'est la période des promus sur Loop, ne manquer pas nos soldes pouvant aller jusqu'à -60%<br><br>" +
                            "Et venez découvrir nos nouvelles voitures fraichement arrivé comme la Peugeot 208 édition GTLine, la Audi R8 ou encore la toute nouvelle Mercedes Classe A. " +
                            "<br><br>L'équipe Loop vous dis à bientot !";
                    break;
            }

            if(client != null && !emailSubject.isEmpty() && !emailContent.isEmpty()){
                    new GMailerUtils().sendMail(emailSubject, emailContent, client.getMail());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/mailController-servlet")
    @ResponseBody
    public String doGet() {
        List<ClientEntity> listClient = (List<ClientEntity>) clientService.findAll();
        for(ClientEntity client : listClient){
            sendMail(session1,"publicity",client);
        }
        return "ok";
    }
}
