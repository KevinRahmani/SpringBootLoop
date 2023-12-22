package springLoop.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springLoop.user.model.AdminEntity;
import springLoop.user.model.ClientEntity;
import springLoop.user.model.VendeurEntity;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {
    Optional<AdminEntity> findByMailAndPassword(String email, String password);

    List<AdminEntity> findAllByMail(String mail);
}
