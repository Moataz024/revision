package com.example.revision.service;

import com.example.revision.entities.Bank;
import com.example.revision.entities.Compte;
import com.example.revision.entities.Transaction;
import com.example.revision.entities.TypeTransaction;

import java.util.List;

public interface IExamenService {
    Bank ajouterBank(Bank bank);

    Compte ajouterCompteEtAffecterAAgence(Compte compte,
                                          String agenceBank);

    String ajouterVirement(Transaction transaction);

    String ajouterRetrait(Transaction transaction);

    String ajouterVersement(Transaction transaction);

    List<Transaction> getAllTransactionsByBankId(Long idBank);

    List<Transaction> getAllTransactionByTypeAndCompte(String type, Long idCompte);
}
