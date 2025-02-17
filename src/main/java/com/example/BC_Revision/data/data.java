package com.example.BC_Revision.data;

import com.example.BC_Revision.dto.*;
import com.example.BC_Revision.service.BorneService;
import com.example.BC_Revision.service.LieuService;
import com.example.BC_Revision.service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@AllArgsConstructor
public class data implements CommandLineRunner {

    private LieuService lieuService;
    private BorneService borneService;
    private UtilisateurService utilisateurService;

    @Override
    public void run(String... args) throws Exception {
        UtilisateurDto u1 = new UtilisateurDto();
        u1.setNom("Rouve");
        u1.setPrenom("Jean-Paul");
        u1.setUsername("JP");
        u1.setRole("admin");
        u1.setEmail("jprouve@test.fr");
        u1.setMotDePasse("test");
        u1.setDateDeNaissance(LocalDate.parse("2023-05-01"));
        u1.setNumeroRue(2);
        u1.setNomRue("rue des robins des bois");
        u1.setCodePostal("63100");
        u1.setVille("Clermont-Ferrand");
        u1.setTelephone("+33 0601020304");
        utilisateurService.saveUtilisateur(u1);

        LieuDto l1 = new LieuDto();
        l1.setNomRue("rue Aristide Briand");
        l1.setVille("Rodez");
        l1.setCodePostal("12000");
        lieuService.saveLieu(l1);

        BorneDto b1 = new BorneDto();
        b1.setNomBorne("Borne 1");
        b1.setInstruction("Tournez à droite après le croisement de la boulangerie et a borne se trouve à 30m.");
        b1.setEstDisponible(true);
        b1.setLatitude(45.76567840576172);
        b1.setLongitude(3.125333309173584);
        b1.setUtilisateurId(1L);
        b1.setPuissance("7.4");
        b1.setPrix(2F);
        b1.setSurPied(true);
        b1.setLieuId(1L);
        b1.setUtilisateurId(1L);
        borneService.saveBorne(b1);

        ReservationDto r1 = new ReservationDto();
        r1.setDateDebut(LocalDate.parse("2024-12-01"));
        r1.setDateFin(LocalDate.parse("2024-12-13"));
        r1.setHeureDebut(LocalTime.parse(""));

        MediaDto m1 = new MediaDto();
        m1.setLibelle("photo borne");
        m1.setTypeMedia("jpg");
        m1.setBorneId(1L);
    }
}
