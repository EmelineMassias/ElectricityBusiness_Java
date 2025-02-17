package com.example.BC_Revision.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    private String typeMedia;

    @ManyToOne
    private Borne borne;

    public Media(Long id, String libelle,
                 String typeMedia, Borne borne) {
        this.id = id;
        this.libelle = libelle;
        this.typeMedia = typeMedia;
        this.borne = borne;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getTypeMedia() {
        return typeMedia;
    }

    public void setTypeMedia(String typeMedia) {
        this.typeMedia = typeMedia;
    }

    public Borne getBorne() {
        return borne;
    }

    public void setBorne(Borne borne) {

        this.borne = borne;
    }

    @Override
    public String toString() {
        return "Media{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", typeMedia='" + typeMedia + '\'' +
                ", borne=" + borne +
                '}';
    }

    public Long getBorneId() {
        return null;
    }
}
