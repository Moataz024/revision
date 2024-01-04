package com.example.revision.controller;


import com.example.revision.entities.Bank;
import com.example.revision.entities.Compte;
import com.example.revision.entities.Transaction;
import com.example.revision.entities.TypeTransaction;
import com.example.revision.service.ExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ExamenController {
    @Autowired
    ExamenService examenService;


    @PostMapping("/bank")
    public Bank ajouterBank(@RequestBody Bank bank){
        return examenService.ajouterBank(bank);
    }

    @PutMapping("/compte/{agenceBank}")
    public Compte ajouterCompteEtAffecterAAgence(@RequestBody Compte compte,@PathVariable("agenceBank") Object agenceBank){
        String agence = agenceBank.toString();
        return examenService.ajouterCompteEtAffecterAAgence(compte,agence);
    }

    @PutMapping("/virement")
    public String ajouterVirement(@RequestBody Transaction transaction){
        return examenService.ajouterVirement(transaction);
    }

    @PutMapping("/retrait")
    public String ajouterRetrait(@RequestBody Transaction transaction){
        return examenService.ajouterRetrait(transaction);
    }

    @PutMapping("/versement")
    public String ajouterVersement(@RequestBody Transaction transaction){
        return examenService.ajouterVersement(transaction);
    }

    @GetMapping("/transaction/{idBank}")
    public List<Transaction> getAllTransactionByBankId(@PathVariable("idBank") Long idBank){
        return examenService.getAllTransactionsByBankId(idBank);
    }

    @GetMapping("/transaction/{type}/{idCompte}")
    public List<Transaction> getAllTransactionByTypeAndCompte(@PathVariable("type") TypeTransaction type, @PathVariable("idCompte") Long idCompte){
        String typeTr = type.toString();
        return examenService.getAllTransactionByTypeAndCompte(typeTr,idCompte);
    }



}
