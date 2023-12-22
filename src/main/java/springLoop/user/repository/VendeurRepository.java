package springLoop.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springLoop.user.model.VendeurEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendeurRepository extends JpaRepository<VendeurEntity, Integer> {
    Optional<VendeurEntity> findByMailAndPassword(String email, String password);

    List<VendeurEntity> findAllByMail(String mail);


    Optional<VendeurEntity> findByNom(String name);
}
