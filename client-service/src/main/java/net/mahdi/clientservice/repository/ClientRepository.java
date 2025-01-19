package net.mahdi.clientservice.repository;

import net.mahdi.clientservice.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
