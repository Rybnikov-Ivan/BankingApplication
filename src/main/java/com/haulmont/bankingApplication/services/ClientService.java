package com.haulmont.bankingApplication.services;

import com.haulmont.bankingApplication.models.Client;
import com.haulmont.bankingApplication.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    public ClientService(){}

    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    public void save(Client client){
        clientRepository.save(client);
    }

    public void remove(Client client){
        clientRepository.delete(client);
    }

}
