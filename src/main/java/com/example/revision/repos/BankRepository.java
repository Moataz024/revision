package com.example.revision.repos;

import com.example.revision.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {

    Bank getBankByAgence(String agence);
}