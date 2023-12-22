package springLoop.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springLoop.user.model.ClientEntity;
import springLoop.user.model.VendeurEntity;
import springLoop.user.service.ClientService;
import springLoop.user.service.VendeurService;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class VerifyData {

    @Autowired
    ClientService clientService;

    @Autowired
    VendeurService vendeurService;

    public static boolean verifyParameters(String nom, String marque,String type, String couleur,String description, String categorie) {
        boolean isValid = true;
        String errorMessage = "Les champs suivants sont invalides : ";

        // Vérification des paramètres de texte
        if (nom == null || nom.isEmpty()) {
            isValid = false;
            errorMessage += "Nom, ";
        }

        if (marque == null || marque.isEmpty()) {
            isValid = false;
            errorMessage += "Marque, ";
        }

        if (type == null || type.isEmpty()) {
            isValid = false;
            errorMessage += "Type, ";
        }

        if (couleur == null || couleur.isEmpty()) {
            isValid = false;
            errorMessage += "Couleur, ";
        }

        if (description == null || description.isEmpty()) {
            isValid = false;
            errorMessage += "Description, ";
        }

        if (categorie == null || categorie.isEmpty()) {
            isValid = false;
            errorMessage += "Categorie, ";
        }

        if (!isValid) {
            System.out.println(errorMessage);
        }

        return isValid;
    }

    public static boolean isVendeurEmail(String email) {
        String domain = "@loop.com";
        return email.endsWith(domain);
    }

    public static boolean isAdminEmail(String email){
        String domain = "@adminloop.com";
        return email.endsWith(domain);
    }

    public static boolean isValidMail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }


    public boolean isFreeMailClient(String mail, ClientEntity client) {

        List<ClientEntity> clients = (List<ClientEntity>) clientService.findAllByMail(mail);
        clients.remove(client);

        return clients.isEmpty();
    }

    public boolean isFreeMailVendeur(String mail, VendeurEntity vendeur) {


        List<VendeurEntity> vendeurs = (List<VendeurEntity>) vendeurService.findAllByMail(mail);
        vendeurs.remove(vendeur);

        return vendeurs.isEmpty();
    }

    public static int getParameterAsInt(String param) throws NumberFormatException {
        int result = 0;
        if (param != null) {
            try {
                result = Integer.parseInt(param);
            } catch (NumberFormatException e) {
                throw new NumberFormatException();
            }
        }
        return result;
    }

    public static int getParameterAsIntV2(String param) throws NumberFormatException {
        if (param != null) {
            return Integer.parseInt(param);
        } else {
            throw new NumberFormatException();
        }
    }

    public static boolean isHashMapEmpty(HashMap<?, ?> map) {
        return map == null || map.isEmpty();
    }
}
