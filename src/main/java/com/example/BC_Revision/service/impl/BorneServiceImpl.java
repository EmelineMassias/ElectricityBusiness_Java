package com.example.BC_Revision.service.impl;

import com.example.BC_Revision.dto.BorneDto;
import com.example.BC_Revision.dto.BorneSearchCriteriaDto;
import com.example.BC_Revision.mapper.BorneMapper;
import com.example.BC_Revision.model.*;
import com.example.BC_Revision.repository.*;
import com.example.BC_Revision.service.BorneService;

import org.springframework.security.core.userdetails.UsernameNotFoundException; // <-- Importez cette exception si vous l'utilisez
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorneServiceImpl implements BorneService {

    private BorneMapper borneMapper;
    private BorneRepository borneRepository;
    private UtilisateurRepository utilisateurRepository;
    private LieuRepository lieuRepository;
    private ReservationRepository reservationRepository;
    private MediaRepository mediaRepository;

    private final EntityManager entityManager;

    // Ajustement du constructeur pour inclure tous les dépôts nécessaires
    public BorneServiceImpl(BorneRepository borneRepository, BorneMapper borneMapper, MediaRepository mediaRepository, UtilisateurRepository utilisateurRepository,
                            LieuRepository lieuRepository, ReservationRepository reservationRepository,
                            EntityManager entityManager){
        this.borneRepository = borneRepository;
        this.borneMapper = borneMapper;
        this.mediaRepository = mediaRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.lieuRepository = lieuRepository;
        this.reservationRepository = reservationRepository;
        this.entityManager = entityManager;
    }


    @Override
    public Borne saveBorne(BorneDto borneDto) {
        Borne borne = borneMapper.toEntity(borneDto);


        if(borneDto.getMediasId() != null && !borneDto.getMediasId().isEmpty()) {
            List<Media> medias = mediaRepository
                    .findAllById(borneDto
                            .getMediasId());
            borne.setMedias(medias);
        } else {
            borne.setMedias(new ArrayList<>());
        }

        if(borneDto.getReservationsId() != null && !borneDto.getReservationsId().isEmpty()) {
            List<Reservation> reservations = reservationRepository.findAllById(borneDto.getReservationsId());
            borne.setReservations(reservations);
        } else {
            borne.setReservations(new ArrayList<>());
        }

        if(borneDto.getUtilisateurId() != null){
            Utilisateur utilisateur= utilisateurRepository
                    .findById(borneDto.getUtilisateurId())
                    .orElse(null);
            borne.setUtilisateur(utilisateur);
        } else {
            borne.setUtilisateur(null);
        }
        if(borneDto.getLieuId() != null){
            Lieu lieu = lieuRepository
                    .findById(borneDto.getLieuId())
                    .orElse(null); // Considérez de lancer une exception si le lieu est obligatoire
            borne.setLieu(lieu);
        } else {
            borne.setLieu(null); // S'assurer que la relation est claire
        }

        return borneRepository.save(borne);
    }

    @Override
    public List<BorneDto> getAllBornes() {
        List<Borne> bornes = borneRepository.findAll();
        return bornes.stream().map(borneMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BorneDto getBorneById(Long id){

        return borneRepository
                .findById(id).map(borneMapper::toDto)
                .orElse(null); // Considérez de lancer une BorneInexistanteException ici
    }

    @Override
    public void deleteBorne(Long id) {

        borneRepository.deleteById(id);
    }


    @Override
    public List<BorneDto> getBornesByUtilisateurEmail(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + email));


        List<Borne> bornes = borneRepository.findByUtilisateur(utilisateur);


        return bornes.stream()
                .map(borneMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BorneDto> searchBornes(BorneSearchCriteriaDto criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Borne> query = cb.createQuery(Borne.class);
        Root<Borne> borne = query.from(Borne.class);
        System.out.println(criteria);
        List<Predicate> predicates = new ArrayList<>();

        // 1. Filtrage par ville
        if (criteria.getVille() != null && !criteria.getVille().isEmpty()) {
            Join<Borne, Lieu> lieu = borne.join("lieu"); // Joindre la relation 'lieu'
            predicates.add(cb.equal(cb.lower(lieu.get("ville")), criteria.getVille().toLowerCase()));
        }

        // 2. Filtrage par disponibilité (dates et heures)
        LocalDateTime searchStartDateTime = null;
        LocalDateTime searchEndDateTime = null;

        if (criteria.getFromDate() != null) {
            searchStartDateTime = (criteria.getStartTime() != null) ?
                    LocalDateTime.of(criteria.getFromDate(), criteria.getStartTime()) :
                    LocalDateTime.of(criteria.getFromDate(), LocalTime.MIN);
        }

        if (criteria.getToDate() != null) {
            searchEndDateTime = (criteria.getEndTime() != null) ?
                    LocalDateTime.of(criteria.getToDate(), criteria.getEndTime()) :
                    LocalDateTime.of(criteria.getToDate(), LocalTime.MAX);
        }

        // Appliquer le filtre de disponibilité SEULEMENT si au moins une date/heure de recherche est fournie
        if (searchStartDateTime != null || searchEndDateTime != null) {
            // La borne doit être marquée comme disponible
            predicates.add(cb.isTrue(borne.get("estDisponible"))); // Assurez-vous que votre entité Borne a 'estDisponible'

            // Construire la sous-requête pour trouver les IDs des bornes qui ONT des réservations chevauchantes
            CriteriaQuery<Long> subquery = cb.createQuery(Long.class);
            Root<Borne> subBorne = subquery.from(Borne.class);
            Join<Borne, Reservation> subReservation = subBorne.join("reservations");

            List<Predicate> overlapPredicates = new ArrayList<>();

            // Logique de chevauchement standard : (start1 < end2 AND end1 > start2)
            // Où intervalle 1 est la réservation (res.start, res.end)
            // et intervalle 2 est la période de recherche (searchStart, searchEnd)

            // res.start < searchEnd
            if (searchEndDateTime != null) {
                overlapPredicates.add(cb.lessThan(
                        cb.function("TIMESTAMP", LocalDateTime.class, subReservation.get("dateDebut"), subReservation.get("heureDebut")),
                        searchEndDateTime
                ));
            }

            // res.end > searchStart
            if (searchStartDateTime != null) {
                overlapPredicates.add(cb.greaterThan(
                        cb.function("TIMESTAMP", LocalDateTime.class, subReservation.get("dateFin"), subReservation.get("heureFin")),
                        searchStartDateTime
                ));
            }

            // Si l'une des dates/heures de recherche n'est pas définie, on peut simplifier le chevauchement.
            // Par exemple, si seulement fromDate est là, on ne s'assure pas que la réservation se termine après fromDate.
            // La logique ci-dessus est pour une période complète [start, end].

            // S'il n'y a pas de prédicats de chevauchement (car searchStart/End étaient null),
            // alors la sous-requête ne devrait rien exclure, ou ce bloc ne devrait pas s'exécuter.
            if (!overlapPredicates.isEmpty()) {
                subquery.select(subBorne.get("id"))
                        .where(cb.and(overlapPredicates.toArray(new Predicate[0])));

                // La borne NE DOIT PAS être dans la liste des bornes qui ont des réservations chevauchantes
                predicates.add(cb.not(borne.get("id").in(subquery)));
            }
        } else {
            // Si aucune date ou heure n'est fournie pour la recherche, inclure toutes les bornes disponibles par défaut.
            // Cela exclurait les bornes marquées 'non disponibles' même si l'utilisateur ne filtre pas par date/heure.
            predicates.add(cb.isTrue(borne.get("estDisponible")));
        }


        // Combiner tous les prédicats
        query.where(cb.and(predicates.toArray(new Predicate[0])));
        query.distinct(true); // Pour éviter les doublons

        // Exécuter la requête
        List<Borne> filteredBornes = entityManager.createQuery(query).getResultList();

        // Debugging: Afficher les bornes trouvées avant le mappage
        System.out.println("Bornes trouvées par Criteria API : " + filteredBornes.size());
        filteredBornes.forEach(b -> System.out.println("- Borne ID: " + b.getId() + ", Ville: " + (b.getLieu() != null ? b.getLieu().getVille() : "N/A")));


        return filteredBornes.stream()
                .map(borneMapper::toDto)
                .collect(Collectors.toList());
    }
}