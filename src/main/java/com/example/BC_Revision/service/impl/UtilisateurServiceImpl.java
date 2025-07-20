package com.example.BC_Revision.service.impl;

import com.example.BC_Revision.dto.UtilisateurDto;
import com.example.BC_Revision.mapper.UtilisateurMapper;
import com.example.BC_Revision.model.Utilisateur;
import com.example.BC_Revision.repository.BorneRepository;
import com.example.BC_Revision.repository.ReservationRepository;
import com.example.BC_Revision.repository.UtilisateurRepository;
import com.example.BC_Revision.service.UtilisateurService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional; // Importez Optional
import java.util.stream.Collectors;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurMapper utilisateurMapper; // Utilisez 'final' pour les injections via constructeur
    private final UtilisateurRepository utilisateurRepository; // Utilisez 'final'
    private final BorneRepository borneRepository; // Utilisez 'final'
    private final ReservationRepository reservationRepository; // Utilisez 'final'



    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository,
                                  UtilisateurMapper utilisateurMapper,
                                  BorneRepository borneRepository,
                                  ReservationRepository reservationRepository){
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurMapper = utilisateurMapper;
        this.borneRepository = borneRepository;
        this.reservationRepository = reservationRepository;
    }

    /**
     * Cette méthode est principalement conçue pour la création d'un utilisateur.
     * Elle gère également la mise à jour si un ID est présent, mais il est recommandé
     * d'utiliser `updateUtilisateur` pour les mises à jour explicites.
     * Le mot de passe DOIT être encodé avant d'appeler cette méthode pour la création.
     *
     * @param utilisateurDto Le DTO de l'utilisateur à sauvegarder ou à créer.
     * @return L'entité Utilisateur sauvegardée.
     */
    @Override
    public Utilisateur saveUtilisateur(UtilisateurDto utilisateurDto) {
        // Pour la création, nous convertissons directement le DTO en entité.
        // Le mot de passe doit déjà être encodé par le contrôleur à ce stade.
        Utilisateur utilisateur = utilisateurMapper.toEntity(utilisateurDto);

        // Si l'ID est présent, cela signifie qu'il pourrait y avoir des relations existantes
        // qui ne sont pas dans le DTO mais que nous ne voulons pas écraser.
        // Cependant, pour une "save" classique (création), nous nous basons sur le DTO.
        // Si vous voulez fusionner, utilisez plutôt la logique de updateUtilisateur.
        // Ici, nous supposons que saveUtilisateur est principalement pour l'insertion de nouveaux utilisateurs.
        // Si un ID est fourni et existe, Spring Data JPA fera une mise à jour.
        // C'est pourquoi j'ai séparé la logique de mise à jour explicite dans updateUtilisateur.

        // Gestion des bornes et réservations pour la CREATION (si les IDs sont présents dans le DTO)
        if (utilisateurDto.getBornesId() != null && !utilisateurDto.getBornesId().isEmpty()) {
            utilisateur.setBornes(borneRepository.findAllById(utilisateurDto.getBornesId()));
        }
        if (utilisateurDto.getReservationsId() != null && !utilisateurDto.getReservationsId().isEmpty()) {
            utilisateur.setReservations(reservationRepository.findAllById(utilisateurDto.getReservationsId()));
        }

        // Le rôle est généralement défini lors de la création ou via une logique métier spécifique,
        // pas systématiquement écrasé par le DTO lors d'une simple sauvegarde.

        return utilisateurRepository.save(utilisateur);
    }

    /**
     * Met à jour un utilisateur existant.
     * Cette méthode est robuste pour les mises à jour partielles (PATCH) ou complètes (PUT).
     *
     * @param utilisateurDto Le DTO contenant les informations de l'utilisateur à mettre à jour.
     * L'ID de l'utilisateur doit être présent dans ce DTO.
     * @return L'entité Utilisateur mise à jour.
     * @throws RuntimeException si l'utilisateur n'est pas trouvé.
     */
    @Override
    public Utilisateur updateUtilisateur(UtilisateurDto utilisateurDto) {
        // Assurez-vous que l'ID est présent pour une mise à jour
        if (utilisateurDto.getId() == null) {
            throw new IllegalArgumentException("L'ID de l'utilisateur est obligatoire pour la mise à jour.");
        }

        // Récupérer l'utilisateur existant
        Utilisateur existingUtilisateur = utilisateurRepository.findById(utilisateurDto.getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + utilisateurDto.getId()));

        // Mettre à jour les champs si les valeurs sont présentes dans le DTO
        // Cela permet des mises à jour partielles (PATCH-like behavior)
        if (utilisateurDto.getEmail() != null) {
            existingUtilisateur.setEmail(utilisateurDto.getEmail());
        }
        if (utilisateurDto.getNom() != null) {
            existingUtilisateur.setNom(utilisateurDto.getNom());
        }
        if (utilisateurDto.getPrenom() != null) {
            existingUtilisateur.setPrenom(utilisateurDto.getPrenom());
        }
        if (utilisateurDto.getNumeroRue() != null) {
            existingUtilisateur.setNumeroRue(utilisateurDto.getNumeroRue());
        }
        if (utilisateurDto.getNomRue() != null) {
            existingUtilisateur.setNomRue(utilisateurDto.getNomRue());
        }
        if (utilisateurDto.getCodePostal() != null) {
            existingUtilisateur.setCodePostal(utilisateurDto.getCodePostal());
        }
        if (utilisateurDto.getVille() != null) {
            existingUtilisateur.setVille(utilisateurDto.getVille());
        }
        if (utilisateurDto.getTelephone() != null) {
            existingUtilisateur.setTelephone(utilisateurDto.getTelephone());
        }
        if (utilisateurDto.getUsername() != null) {
            existingUtilisateur.setUsername(utilisateurDto.getUsername());
        }
        if (utilisateurDto.getDateDeNaissance() != null) {
            existingUtilisateur.setDateDeNaissance(utilisateurDto.getDateDeNaissance());
        }
        if (utilisateurDto.getPhotoProfil() != null) {
            existingUtilisateur.setPhotoProfil(utilisateurDto.getPhotoProfil());
        }

        // Gérer le mot de passe UNIQUEMENT si un nouveau mot de passe est fourni et non vide.
        // Le contrôleur est responsable de l'encodage du nouveau mot de passe avant de l'envoyer au service.
        if (utilisateurDto.getMotDePasse() != null && !utilisateurDto.getMotDePasse().isEmpty()) {
            existingUtilisateur.setMotDePasse(utilisateurDto.getMotDePasse());
        }

        // Gérer les bornes : Si la liste des IDs est fournie (non nulle), nous la mettons à jour.
        // Si elle est vide, cela signifie potentiellement supprimer toutes les bornes liées.
        // Si elle est nulle, nous ne touchons pas aux bornes existantes.
        if (utilisateurDto.getBornesId() != null) {
            existingUtilisateur.setBornes(borneRepository.findAllById(utilisateurDto.getBornesId()));
        }

        // Gérer les réservations : Même logique que pour les bornes.
        if (utilisateurDto.getReservationsId() != null) {
            existingUtilisateur.setReservations(reservationRepository.findAllById(utilisateurDto.getReservationsId()));
        }

        // Le rôle n'est généralement pas mis à jour via ce DTO. Si un rôle doit être modifié,
        // cela devrait être une opération distincte ou une logique métier spécifique.

        return utilisateurRepository.save(existingUtilisateur);
    }

    @Override
    public List<UtilisateurDto> getAllUtilisateurs(){
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        return utilisateurs.stream().map(utilisateurMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UtilisateurDto getUtilisateurById(Long id){
        // Utilisation de orElseThrow pour une meilleure gestion des erreurs si l'utilisateur n'est pas trouvé
        return utilisateurRepository
                .findById(id).map(utilisateurMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + id));
    }

    @Override
    public void deleteUtilisateur(Long id){
        // Vérifiez si l'utilisateur existe avant de supprimer pour éviter une NoSuchElementException cachée
        if (!utilisateurRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé avec l'ID : " + id);
        }
        utilisateurRepository.deleteById(id);
    }

    @Override
    public UtilisateurDto getUtilisateurByEmail(String email){
        // Utilisation de orElseThrow pour une meilleure gestion des erreurs si l'utilisateur n'est pas trouvé
        return utilisateurRepository.findByEmail(email).map(utilisateurMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'email : " + email));
    }
}