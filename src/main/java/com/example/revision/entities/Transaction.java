package com.example.revision.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idTransaction;
    private Double montant;
    @Enumerated(EnumType.STRING)
    private TypeTransaction type;
    @Temporal(TemporalType.DATE)
    private Date dateTransaction;

    @ManyToOne()
    private Compte exp;

    @ManyToOne()
    private Compte dest;
}