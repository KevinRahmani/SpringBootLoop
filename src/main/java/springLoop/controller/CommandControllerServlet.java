package springLoop.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springLoop.basket.service.BasketService;
import springLoop.product.model.ArticleEntity;
import springLoop.product.service.ArticleService;
import springLoop.user.model.AdminEntity;
import springLoop.user.model.ClientEntity;
import springLoop.user.model.VendeurEntity;
import springLoop.user.service.AdminService;
import springLoop.user.service.ClientService;
import springLoop.user.service.VendeurService;
import springLoop.utils.CalculationUtils;
import springLoop.utils.ProcessBasketService;
import springLoop.utils.VerifyData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Controller
@MultipartConfig
public class CommandControllerServlet {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private VendeurService vendeurService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private ClientService clientService;
    @Resource
    private HttpSession session;
    String status;

    @PostMapping("/commandController-servlet")
    @ResponseBody
    public String doPost(
            @RequestParam(name = "name-card") String nameCard,
            @RequestParam(name = "number-card") String numberCard,
            @RequestParam(name = "date-card") String dateCard,
            @RequestParam(name = "cvc-card") String cvcCard
    ) {
        if (VerifyData.verifyParameters(numberCard, nameCard, cvcCard, dateCard, "testSake", "testSake")) {
            status = "ok";
            //Parameters
            ClientEntity client = (ClientEntity) session.getAttribute("user");
            BasketService basketService = ProcessBasketService.getBasketSession(session);
            String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("dd:MM:yyyy"));
            double totalPriceTTC = BigDecimal.valueOf(basketService.getTotalPrice()).setScale(2, RoundingMode.HALF_UP).doubleValue(); // 2 significance numbers

            //remove quantity from stock and add sales quantity
            for (Map.Entry<ArticleEntity, Integer> entry : basketService.getBasket().getPanier().entrySet()) {
                //remove stock quantity part
                ArticleEntity article = articleService.findById(entry.getKey().getId());
                //check if article was found and stock > quantityRequested
                if (article != null && article.getStock() >= entry.getValue()) {
                    //article part
                    article.setStock(article.getStock() - entry.getValue()); // stock article = previous stock - quantityRequested
                    article.setSales(article.getSales() + entry.getValue()); //sales article = previous sales + quantityRequested
                    articleService.save(article);

                    //client Part
                    client.setNbproduct(client.getNbproduct() + entry.getValue()); //nbProduct of client = previous + quantityRequested
                    client.setHistocommand(client.getHistocommand() + "<br><br>" + dateStr + " : " + article.getMarque() + article.getNom() + "<br>Quantite : " + entry.getValue());
                    clientService.save(client);

                    //vendeur or Admin part
                    if (article.getVendeur().equals("Loop")) {
                        AdminEntity admin = adminService.findById(19042003); //find admin by ID
                        admin.setNbtotalsales(admin.getNbtotalsales() + (entry.getValue() * article.getPrix())); //add the value of the line command : price of article * quantity
                        adminService.save(admin);
                    } else {
                        VendeurEntity vendeur = vendeurService.findByNom(article.getVendeur());
                        vendeur.setNbtotalsales(vendeur.getNbtotalsales() + (entry.getValue()) * article.getPrix()); //same
                        vendeurService.save(vendeur);
                    }
                }
            }
            client.setFidelity(client.getFidelity() + CalculationUtils.calculateFidelityPoint(totalPriceTTC));
            if (client.getFidelity() > 500) {
                totalPriceTTC *= 0.95; // 5% retrieved
                client.setFidelity(client.getFidelity() - 500);
                clientService.save(client);
            }

            //mail to send
            session.setAttribute("totalPriceTTC", totalPriceTTC);
            MailControllerServlet.sendMail(session, "command", client);

            //delete session attribute basket and hashmapBasket
            session.removeAttribute("basket");
            session.removeAttribute("hashmapBasket");
            session.removeAttribute("totalPriceTTC");
        } else {
            status = "Champs manquants ou incorrects";
        }
        return status;

    }
}
