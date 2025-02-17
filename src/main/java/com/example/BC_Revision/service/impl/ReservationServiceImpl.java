package com.example.BC_Revision.service.impl;

import com.example.BC_Revision.dto.ReservationDto;
import com.example.BC_Revision.mapper.ReservationMapper;
import com.example.BC_Revision.model.Borne;
import com.example.BC_Revision.model.Reservation;
import com.example.BC_Revision.model.Utilisateur;
import com.example.BC_Revision.repository.BorneRepository;
import com.example.BC_Revision.repository.ReservationRepository;
import com.example.BC_Revision.repository.UtilisateurRepository;
import com.example.BC_Revision.service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {


    private ReservationMapper reservationMapper;
    private ReservationDto reservationDto;
    private ReservationRepository reservationRepository;
    private BorneRepository borneRepository;
    private UtilisateurRepository utilisateurRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationMapper reservationMapper,
                                  BorneRepository borneRepository, UtilisateurRepository utilisateurRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.borneRepository = borneRepository;
        this.utilisateurRepository = utilisateurRepository;
    }


    @Override
    public Reservation saveReservation(ReservationDto reservationDto) {
        Reservation reservation = reservationMapper.toEntity(reservationDto);
        if(reservationDto.getBorneId() != null) {
            Borne borne = borneRepository
                    .findById(reservationDto
                            .getBorneId())
                    .orElse(null);
            reservation.setBorne(borne);

        }
        if(reservationDto.getUtilisateurId() != null) {
            Utilisateur utilisateur = utilisateurRepository.findById(reservationDto.getUtilisateurId()).orElse(null);
            reservation.setUtilisateur(utilisateur);
        }
        return reservationRepository.save(reservation);
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream().map(reservationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDto getReservationById(Long id) {
        return reservationRepository
                .findById(id).map(reservationMapper::toDto)
                .orElse(null);
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}


