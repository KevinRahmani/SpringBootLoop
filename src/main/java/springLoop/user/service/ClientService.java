package springLoop.user.service;

import ch.qos.logback.core.net.server.Client;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springLoop.user.model.ClientEntity;
import springLoop.user.model.VendeurEntity;
import springLoop.user.repository.ClientRepository;

import java.util.List;

@Data
@Service
public class ClientService {

    private final ClientRepository clientRepository;
    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientEntity findById(int id) {
        return clientRepository.findById(id).orElse(null);
    }

    public void save(ClientEntity a){
        clientRepository.save(a);
    }

    public void delete(ClientEntity a){
        clientRepository.delete(a);
    }

    public Iterable<ClientEntity> findAll() {
        return clientRepository.findAll();
    }

    public ClientEntity connect(String email, String password) {

        List<ClientEntity> results = clientRepository.findAllByMail(email);

        //il faut vérifier BCrypt.checkpw(password, t.getPassword());
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }

    public Iterable<ClientEntity> findAllByMail(String mail){
        return clientRepository.findAllByMail(mail);
    }
}
