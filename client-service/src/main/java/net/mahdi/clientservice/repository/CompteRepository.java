

package net.mahdi.clientservice.repository;

import net.mahdi.clientservice.enums.TypeCompte;
import net.mahdi.clientservice.models.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CompteRepository extends JpaRepository<Compte,Long > {
    Optional<Compte> findByRib(String rib);

    List<Compte> findAllByTypeCompte(TypeCompte typeCompte);
}
