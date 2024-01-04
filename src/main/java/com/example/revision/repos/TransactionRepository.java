package com.example.revision.repos;

import com.example.revision.entities.Compte;
import com.example.revision.entities.Transaction;
import com.example.revision.entities.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByDateTransaction(Date dateTransaction);


    @Query("SELECT t FROM Transaction t JOIN t.dest td JOIN t.exp te ON (td.bank.idBank = :idBank OR te.bank.idBank = :idBank)")
    List<Transaction> getAllTransactionByBankId(@Param("idBank") Long idBank);

    List<Transaction> findAllByTypeAndDest_IdCompteOrExp_IdCompte(TypeTransaction type, Long dest_idCompte, Long exp_idCompte);
}