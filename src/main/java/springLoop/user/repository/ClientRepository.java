package springLoop.user.repository;

import ch.qos.logback.core.net.server.Client;
import springLoop.user.model.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springLoop.user.model.VendeurEntity;

import java.util.List;
import java.util.Optional;


@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {
    Optional<ClientEntity> findByMailAndPassword(String email, String password);

    List<ClientEntity> findAllByMail(String mail);

}
