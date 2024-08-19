package com.example.demo.service;


import com.example.demo.mapper.ClientMapper;
import com.example.demo.mapper.TrainerMapper;
import com.example.demo.model.Client;
import com.example.demo.model.Trainer;
import com.example.demo.model.dto.ClientDTO;
import com.example.demo.model.dto.TrainerDTO;
import com.example.demo.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public List<Client> addClient(List<Client> clients){
        List<ClientDTO> clientDTOs = clients
                .stream()
                .map(ClientMapper.INSTANCE::clientToClientDTO)
                .collect(Collectors.toList());
                //il metodo sotto viene utlizzato nel caso in cui si vuole aggiungere solo un client, ma obsoleto perchè grazie spring.jackson.deserialization.accept-single-value-as-array=true l'array viene accettata anche nel caso in cui ho solo un elemento
                //ClientMapper.INSTANCE.clientToClientDTO(client);
        List<ClientDTO> saved = clientRepository.saveAll(clientDTOs);
        return saved.stream().map(ClientMapper.INSTANCE::clientDTOToClient).collect(Collectors.toList());
    }

    @Transactional
    public List<Client> getAllClient(){

        return  clientRepository
                .findAll()
                .stream()
                .map(ClientMapper.INSTANCE::clientDTOToClient)
                .toList();
    }

    @Transactional
    public Client updateClient(Client client) throws ParseException {

        Client preClient = clientRepository.findById(client.getClientID())
                .map(ClientMapper.INSTANCE::clientDTOToClient)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        Client updateClient = Client.builder()
                .clientID(preClient.getClientID())
                .trainingCardID(client.getTrainingCardID() != null ? client.getTrainingCardID() : preClient.getTrainingCardID())
                .subscriptionID(client.getSubscriptionID() != null ? client.getSubscriptionID() : preClient.getSubscriptionID())
                .name(client.getName() != null && !client.getName().isEmpty() ? client.getName() : preClient.getName())
                .surname(client.getSurname() != null && !client.getSurname().isEmpty() ? client.getSurname() : preClient.getSurname())
                .bday(client.getBday() != null ? formatter.format( client.getBday()) : formatter.format(preClient.getBday()))
                .address(client.getAddress() != null && !client.getAddress().isEmpty() ? client.getAddress() : preClient.getAddress())
                .mobile(client.getMobile() != null && !client.getMobile().isEmpty() ? client.getMobile() : preClient.getMobile())
                .email(client.getEmail() != null && !client.getEmail().isEmpty() ? client.getEmail() : preClient.getEmail())
                .build();

        ClientDTO updateClientDTO = ClientMapper.INSTANCE.clientToClientDTO(updateClient);
        ClientDTO savedClient = clientRepository.save(updateClientDTO);

        return ClientMapper.INSTANCE.clientDTOToClient(savedClient);
    }


    @Transactional
    public Client getClientById(long id) {
        return clientRepository.findById(id)
                .map(ClientMapper.INSTANCE::clientDTOToClient).orElse(null);
    }


    @Transactional
    public void deleteClientById(long id) {
        clientRepository.deleteById(id);
    }


}
