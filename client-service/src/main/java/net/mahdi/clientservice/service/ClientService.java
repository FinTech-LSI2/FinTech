package net.mahdi.clientservice.service;

import net.mahdi.clientservice.DTOs.ClientDTO;
import net.mahdi.clientservice.models.Client;
import net.mahdi.clientservice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client createClient(ClientDTO clientDTO) {
        System.out.println("Received CIN: " + clientDTO.getCIN());  // Log pour vérifier la valeur de CIN
        Client client = new Client();
        client.setLastname(clientDTO.getLastname());
        client.setFirstname(clientDTO.getFirstname());
        client.setCIN(clientDTO.getCIN());
        client.setEmail(clientDTO.getEmail());
        client.setNumeroTelephone(clientDTO.getNumeroTelephone());
        client.setDateNaissance(clientDTO.getDateNaissance());
        client.setAdresse(clientDTO.getAdresse());
        client.setNatureClient(clientDTO.getNatureClient());
        client.setPassword(clientDTO.getPassword());
        client.setInscriptionDate(LocalDate.now());
        // Sauvegarde dans la base de données
        return clientRepository.save(client);
    }

    public boolean deleteClient(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            clientRepository.delete(client.get());
            return true;
        }
        return false;
    }
}