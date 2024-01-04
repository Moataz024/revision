package com.example.revision.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Bank implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idBank;
    private String nom;
    private String agence;
    private String adresse;

    @OneToMany(mappedBy = "bank")
    @JsonManagedReference
    @ToString.Exclude
    private List<Compte> comptes;
}
