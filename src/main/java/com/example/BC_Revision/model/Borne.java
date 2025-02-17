package com.example.BC_Revision.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Borne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomBorne;

    private String puissance;

    private Boolean estDisponible;

    private String instruction;

    private Boolean surPied;

    private Double latitude;

    private Double longitude;

    private Float prix;

    private String photo;

    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToOne
    private Lieu lieu;

    @OneToMany(mappedBy = "borne", fetch = FetchType.EAGER)
    private List<Media> medias = new ArrayList<>();

    @OneToMany(mappedBy="borne", fetch = FetchType.EAGER)
    private List<Reservation> reservations = new ArrayList<>();


    public Borne(Long id, String nomBorne, String puissance, Boolean estDisponible, String instruction, Boolean surPied, Double latitude, Double longitude, Float prix, String photo, Utilisateur utilisateur, Lieu lieu, List<Media> medias) {
        this.id = id;
        this.nomBorne = nomBorne;
        this.puissance = puissance;
        this.estDisponible = estDisponible;
        this.instruction = instruction;
        this.surPied = surPied;
        this.latitude = latitude;
        this.longitude = longitude;
        this.prix = prix;
        this.photo = photo;
        this.utilisateur = utilisateur;
        this.lieu = lieu;
        this.medias = medias;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomBorne() {
        return nomBorne;
    }

    public void setNomBorne(String nomBorne) {
        this.nomBorne = nomBorne;
    }

    public String getPuissance() {
        return puissance;
    }

    public void setPuissance(String puissance) {
        this.puissance = puissance;
    }

    public Boolean getEstDisponible() {
        return estDisponible;
    }

    public void setEstDisponible(Boolean estDisponible) {
        this.estDisponible = estDisponible;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Boolean getSurPied() {
        return surPied;
    }

    public void setSurPied(Boolean surPied) {
        this.surPied = surPied;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    @Override
    public String toString() {
        return "Borne{" +
                "id=" + id +
                ", nomBorne='" + nomBorne + '\'' +
                ", puissance='" + puissance + '\'' +
                ", estDisponible=" + estDisponible +
                ", instruction='" + instruction + '\'' +
                ", surPied=" + surPied +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", prix=" + prix +
                ", photo='" + photo + '\'' +
                ", utilisateur=" + utilisateur +
                ", lieu=" + lieu +
                ", medias=" + medias +
                '}';
    }
}
