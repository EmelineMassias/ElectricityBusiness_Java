package com.example.BC_Revision.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Lieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected String nomRue;

    protected String codePostal;

    protected String ville;

    @OneToMany(mappedBy = "lieu")
    private List<Borne> bornes;

    public Lieu(Long id, String nomRue, String codePostal, String ville, List<Borne> bornes) {
        this.id = id;
        this.nomRue = nomRue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.bornes = bornes;
    }

    public Lieu(String nomRue, String codePostal, String ville, List<Borne> bornes) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomRue() {
        return nomRue;
    }

    public void setNomRue(String nomRue) {
        this.nomRue = nomRue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public List<Borne> getBornes() {
        return bornes;
    }

    public void setBornes(List<Borne> bornes) {
        this.bornes = bornes;
    }

    @Override
    public String toString() {
        return "Lieu{" +
                "id=" + id +
                ", nomRue='" + nomRue + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                ", bornes=" + bornes +
                '}';
    }
}
