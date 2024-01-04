package com.example.revision.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Compte implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idCompte;
    @Enumerated(EnumType.STRING)
    private TypeCompte type;
    private Long code;
    private Double solde;

    @OneToMany()
    @JsonIgnore
    @ToString.Exclude
    private List<Transaction> transactionExp;

    @OneToMany()
    @JsonIgnore
    @ToString.Exclude
    private List<Transaction> transactionDest;

    @JsonBackReference
    @ManyToOne()
    private Bank bank;

}
