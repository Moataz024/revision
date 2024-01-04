package com.example.revision.service;


import com.example.revision.entities.*;
import com.example.revision.repos.BankRepository;
import com.example.revision.repos.CompteRepository;
import com.example.revision.repos.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ExamenService implements IExamenService{
    @Autowired
    BankRepository bankRepository;
    @Autowired
    CompteRepository compteRepository;
    @Autowired
    TransactionRepository transactionRepository;


    @Override
    public Bank ajouterBank(Bank bank){
        return bankRepository.save(bank);
    }

    @Override
    public Compte ajouterCompteEtAffecterAAgence(Compte compte, String agenceBank){
        Bank agence = bankRepository.getBankByAgence(agenceBank);
        compte.setBank(agence);
        Compte saved = compteRepository.save(compte);
        List<Compte> comptes = new ArrayList<>();
        comptes.add(saved);
        comptes.addAll(agence.getComptes());
        agence.setComptes(comptes);
        bankRepository.save(agence);
        return saved;
    }

    @Override
    public String ajouterVirement(Transaction transaction){
        transaction.setDateTransaction(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Compte exp = compteRepository.findById(transaction.getExp().getIdCompte()).get();
        Compte dest = compteRepository.findById(transaction.getDest().getIdCompte()).get();

        if(transaction.getType() == TypeTransaction.VIREMENT && exp.getType() == TypeCompte.EPARGNE){
            return " On ne peut pas faire un virement à partir\n" +
                    "d’un compte épargne";
        }
        if(transaction.getType() == TypeTransaction.VIREMENT && transaction.getMontant()+3 > exp.getSolde()){
            return "On ne peut pas faire un virement : Solde\n" +
                    "insuffisant";
        }

        if(transaction.getType() != TypeTransaction.VIREMENT){
            return "Veuillez vérifier le type de transaction";
        }

        exp.setSolde(exp.getSolde()-3 - transaction.getMontant());
        dest.setSolde(dest.getSolde() + transaction.getMontant());

        compteRepository.save(exp);
        compteRepository.save(dest);
        transactionRepository.save(transaction);
        return " VIREMENT de "+transaction.getMontant()+" DT de compte "+transaction.getExp().getIdCompte()+" vers le compte "+transaction.getDest().getIdCompte()+" approuvé avec\n" +
                "succès";
    }

    @Override
    public String ajouterRetrait(Transaction transaction){
        transaction.setDateTransaction(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Transaction tr = transactionRepository.save(transaction);
        Compte exp = compteRepository.findById(transaction.getExp().getIdCompte()).get();
        if(transaction.getType() == TypeTransaction.RETRAIT && transaction.getMontant()+2 > exp.getSolde()){
            transactionRepository.deleteById(tr.getIdTransaction());
            return "On ne peut pas faire un\n" +
                    "retrait : Solde insuffisant";
        }else {
            exp.setSolde(exp.getSolde()-(transaction.getMontant()+2));
            exp.setBank(exp.getBank());
            compteRepository.save(exp);
            transactionRepository.save(transaction);
            return "RETRAIT de "+transaction.getMontant()+" DT de compte "+exp.getIdCompte()+" approuvé avec succès";
        }
    }

    @Override
    public String ajouterVersement(Transaction transaction){
        transaction.setDateTransaction(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Compte dest = compteRepository.findById(transaction.getDest().getIdCompte()).get();
        if(transaction.getType() != TypeTransaction.VERSEMENT){
            return "Veuillez vérifier le type de transaction";
        }

        if(transaction.getType() == TypeTransaction.VERSEMENT && dest.getType() != TypeCompte.EPARGNE){
            dest.setSolde(dest.getSolde()+transaction.getMontant()-2);
            compteRepository.save(dest);
            transactionRepository.save(transaction);
            return "Versement de "+transaction.getMontant()+" DT vers compte "+dest.getIdCompte()+" approuvé avec succès";
        }
        dest.setSolde(dest.getSolde()+transaction.getMontant());
        compteRepository.save(dest);
        transactionRepository.save(transaction);
        return "Versement de "+transaction.getMontant()+" DT vers compte "+dest.getIdCompte()+" approuvé avec succès";
    }

    @Scheduled(cron = "*/30 * * * * *")
    public void getAllTransactionByDate(){
        List<Transaction> transactionList = transactionRepository.findAllByDateTransaction(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        transactionList.forEach(transaction -> {
            log.info(transaction.toString());
        });
    }

    @Override
    public List<Transaction> getAllTransactionsByBankId(Long idBank){
        return transactionRepository.getAllTransactionByBankId(idBank);
    }

    @Override
    public List<Transaction> getAllTransactionByTypeAndCompte(String typeTr, Long idCompte){
        TypeTransaction type = TypeTransaction.valueOf(typeTr);
        return transactionRepository.findAllByTypeAndDest_IdCompteOrExp_IdCompte(type,idCompte,idCompte);
    }






}
