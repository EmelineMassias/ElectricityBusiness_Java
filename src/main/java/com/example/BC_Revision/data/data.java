package com.example.BC_Revision.data;

import com.example.BC_Revision.dto.*;
import com.example.BC_Revision.model.*;
import com.example.BC_Revision.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class data implements CommandLineRunner {

    private final LieuRepository lieuRepository;
    private final BorneRepository borneRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ReservationRepository reservationRepository;
    private final MediaRepository mediaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public void run(String... args) throws Exception {
        if(utilisateurRepository.findAll().isEmpty()) {
        Utilisateur u1 = new Utilisateur();
        u1.setNom("Rouve");
        u1.setPrenom("Jean-Paul");
        u1.setUsername("JP");
        u1.setRole("admin");
        u1.setEmail("jprouve@test.fr");
        u1.setMotDePasse(passwordEncoder.encode("password"));
        u1.setDateDeNaissance(LocalDate.now().minusYears(20));
        u1.setNumeroRue(2);
        u1.setNomRue("rue des robins des bois");
        u1.setCodePostal("63100");
        u1.setPhotoProfil("images.jpg");
        u1.setVille("Clermont-Ferrand");
        u1.setTelephone("+33 0601020304");
    

        Lieu l1 = new Lieu();
        l1.setNomRue("rue Aristide Briand");
        l1.setVille("Rodez");
        l1.setCodePostal("12000");


        Borne b1 = new Borne();
        b1.setNomBorne("Borne 1");
        b1.setInstruction("Tournez à droite après le croisement de la boulangerie et a borne se trouve à 30m.");
        b1.setEstDisponible(true);
        b1.setLatitude(45.76567840576172);
        b1.setLongitude(3.125333309173584);
        b1.setUtilisateur(u1);
        b1.setPuissance("7.4");
        b1.setPrix(2F);
        b1.setSurPied(true);
        b1.setLieu(l1);
        b1.setUtilisateur(u1);


        Reservation r1 = new Reservation();
        r1.setDateDebut(LocalDate.parse("2024-12-01"));
        r1.setDateFin(LocalDate.parse("2024-12-13"));
        r1.setHeureDebut(LocalTime.now());
        r1.setHeureFin(LocalTime.now().plusHours(2));
        r1.setUtilisateur(u1);
        r1.setBorne(b1);


        Media m1 = new Media();
        m1.setLibelle("photo borne");
        m1.setTypeMedia("jpg");
        m1.setBorne(b1);


        utilisateurRepository.save(u1);
        lieuRepository.save(l1);
        borneRepository.save(b1);
        reservationRepository.save(r1);
        mediaRepository.save(m1);
    }
 }
}
