package com.example.demo.controller;

import com.example.demo.mapper.ClientMapper;
import com.example.demo.model.Client;
import com.example.demo.model.Subscription;
import com.example.demo.model.dto.ClientDTO;
import com.example.demo.service.ClientService;
import com.example.demo.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/subscription")
// localhost:8080/student/
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private ClientService clientService;

    //@RequestMapping(method = RequestMethod.GET)
    //poichè non siamo più nella preistoria usiamo
    @GetMapping
    //[GET] http://localhost:8080/subscription
    public List<Subscription> getAllSubscription(){
        return  subscriptionService.getAllSubscription();
    }


    //http://localhost:8080/subscription/updateSubscription
    @PutMapping("/updateSubscription")
    public Subscription updateSubscription(@Valid @RequestBody Subscription subscription) throws ParseException {
        return subscriptionService.updateSubscription(subscription);
    }

    //per passare l'id:
    @GetMapping ("/{id}")
    //il risultato sarà [GET] http://localhost:8080/subscription/1
    public Subscription getSubscriptionById(@PathVariable long id) {
        return subscriptionService.getSubscriptionById(id);
    }

    //[POST] http://localhost:8080/subscription
    @PostMapping
    public List<Subscription> addSubscription(@Valid @RequestBody List<Subscription> subscription) {
        return subscriptionService.addSubscription(subscription);
    }

    //[DELETE] http://localhost:8080/subscription
    @DeleteMapping("/{id}")
    public void deleteSubscription(@PathVariable long id) {
        subscriptionService.deleteSubscriptionById(id);

    }



    //[PUT] http://localhost:8080/subscription/addClient?subscription=1&client=1
    @PutMapping("/addClient")
    public ResponseEntity<Subscription> addClientToSubscription(@RequestParam long subscription, @RequestParam long client) throws ParseException {
        Subscription subscriptionById = subscriptionService.getSubscriptionById(subscription);
        Client clientById = clientService.getClientById(client);

        if (subscriptionById == null || clientById == null){
            return ResponseEntity.notFound().build();
        }

        subscriptionById.setClientID(clientById);
        //clientService.updateClient(clientById);
        subscriptionService.updateSubscription(subscriptionById);
        

       return ResponseEntity.ok(subscriptionById);

    }
}
