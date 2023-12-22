package springLoop.user.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springLoop.user.model.AdminEntity;
import springLoop.user.model.ClientEntity;
import springLoop.user.repository.AdminRepository;

import java.util.List;


@Data
@Service
public class AdminService {

    private final AdminRepository adminRepository;
    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public AdminEntity findById(int id) {
        return adminRepository.findById(id).orElse(null);
    }

    public void save(AdminEntity a){
        adminRepository.save(a);
    }

    public void delete(AdminEntity a){
        adminRepository.delete(a);
    }

    public Iterable<AdminEntity> findAll() {
        return adminRepository.findAll();
    }

    public AdminEntity connect(String email, String password) {

        List<AdminEntity> results = adminRepository.findAllByMail(email);

        //il faut vérifier BCrypt.checkpw(password, t.getPassword());
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }
}
