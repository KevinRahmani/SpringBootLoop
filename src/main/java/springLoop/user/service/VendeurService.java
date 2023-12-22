package springLoop.user.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springLoop.user.model.VendeurEntity;
import springLoop.user.repository.VendeurRepository;

import java.util.List;

@Data
@Service
public class VendeurService {

    private final VendeurRepository vendeurRepository;
    @Autowired
    public VendeurService(VendeurRepository vendeurRepository) {
        this.vendeurRepository = vendeurRepository;
    }

    public VendeurEntity findById(int id) {
        return vendeurRepository.findById(id).orElse(null);
    }

    public void save(VendeurEntity a){
        vendeurRepository.save(a);
    }

    public void delete(VendeurEntity a){
        vendeurRepository.delete(a);
    }

    public Iterable<VendeurEntity> findAll() {
        return vendeurRepository.findAll();
    }


    public VendeurEntity connect(String email, String password) {

        List<VendeurEntity> results = vendeurRepository.findAllByMail(email);

        //il faut vérifier BCrypt.checkpw(password, t.getPassword());
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }

    public Iterable<VendeurEntity> findAllByMail(String mail){
        return vendeurRepository.findAllByMail(mail);
    }

    public VendeurEntity findByNom(String name) {
        return vendeurRepository.findByNom(name).orElse(null);
    }
}
