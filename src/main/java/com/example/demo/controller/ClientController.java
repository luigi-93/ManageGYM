package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.model.dto.ClientDTO;
import com.example.demo.service.ClientService;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
// localhost:8080/client/
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {


    private final ClientService clientService;

    @GetMapping("/clientByName")
    public ResponseEntity<List<Client>> getClientByName(@RequestParam String name,
                                                        @RequestParam Integer pageNumber,
                                                        @RequestParam Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<Client> clientList = clientService.listClientByName(name, pageRequest);

        return new ResponseEntity<>(clientList, HttpStatus.OK);
    }


    @GetMapping
    //il risultato sarà [GET]
    public List<Client> getAllClient(){
        return  clientService.getAllClient();
    }

    //[PATCH] http://localhost:8080/client/updateClient

    @PatchMapping("/updateClient")
    public Client updateClient(@Valid @RequestBody Client client ) throws ParseException {
        return clientService.updateClient(client);
    }


    @GetMapping ("/{id}")
    //[GET] http://localhost:8080/client/1
    public Client getClientById(@PathVariable long id) {
        return clientService.getClientById(id);
    }


    //[POST] http://localhost:8080/client/list
    @PostMapping("/list")
    public List<Client> addListClient(@Valid @RequestBody List<Client> client) {
        return clientService.addClients(client);
    }

    //[POST] http://localhost:8080/client
    @PostMapping
    public Client addClient(@Valid @RequestBody Client client) {
        return clientService.addClient(client);
    }


    //[DELETE] http://localhost:8080/client
    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable long id) {
        clientService.deleteClientById(id);

    }
}
