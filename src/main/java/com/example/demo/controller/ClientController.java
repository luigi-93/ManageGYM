package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.model.dto.ClientDTO;
import com.example.demo.service.ClientService;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
// localhost:8080/client/
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;


    @GetMapping
    //il risultato sarà [GET]
    public List<Client> getAllClient(){
        return  clientService.getAllClient();
    }

    // [PUT] http://localhost:8080/client/updateClient
    //da cambiare in patch
    @PutMapping("/updateClient")
    public Client updateClient(@Valid @RequestBody Client client ) throws ParseException {
        return clientService.updateClient(client);
    }

    //per passare l'id:
    @GetMapping ("/{id}")
    //il risultato sarà [GET] http://localhost:8080/client/1
    public Client getClientById(@PathVariable long id) {
        return clientService.getClientById(id);
    }


    //[POST] http://localhost:8080/client
    //creare due endpoint uno che accetta l'array e l'altro nain
    @PostMapping
    public List<Client> addClient(@Valid @RequestBody List<Client> client) {
        return clientService.addClient(client);
    }

    //[DELETE] http://localhost:8080/client
    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable long id) {
        clientService.deleteClientById(id);

    }
}
