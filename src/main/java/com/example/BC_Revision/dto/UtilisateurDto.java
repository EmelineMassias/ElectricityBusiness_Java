package com.example.BC_Revision.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurDto implements Serializable {
    Long id;
    String username;
    String nom;
    String prenom;
    String email;
    String motDePasse;
    String telephone;
    String role;
    LocalDate dateDeNaissance;
    Integer numeroRue;
    String nomRue;
    String codePostal;
    String ville;
    private List<Long> bornesId;
    private List<Long> reservationsId;

}
