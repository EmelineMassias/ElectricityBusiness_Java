package com.example.BC_Revision.service.impl;

import com.example.BC_Revision.dto.UtilisateurDto;
import com.example.BC_Revision.mapper.UtilisateurMapper;
import com.example.BC_Revision.model.Borne;
import com.example.BC_Revision.model.Reservation;
import com.example.BC_Revision.model.Utilisateur;
import com.example.BC_Revision.repository.BorneRepository;
import com.example.BC_Revision.repository.ReservationRepository;
import com.example.BC_Revision.repository.UtilisateurRepository;
import com.example.BC_Revision.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateurMapper utilisateurMapper;
    private UtilisateurDto utilisateurDto;
    private UtilisateurRepository utilisateurRepository;
    private BorneRepository borneRepository;
    private ReservationRepository reservationRepository;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository,
                                  UtilisateurMapper utilisateurMapper,
                                  BorneRepository borneRepository,
                                  ReservationRepository reservationRepository){
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurMapper = utilisateurMapper;
        this.borneRepository = borneRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Utilisateur saveUtilisateur(UtilisateurDto utilisateurDto){
        Utilisateur utilisateur = utilisateurMapper.toEntity(utilisateurDto);
        if(utilisateurDto.getBornesId() != null &&
        utilisateurDto.getBornesId().isEmpty()) {
            List<Borne> bornes = borneRepository.findAllById(utilisateurDto
                    .getBornesId());
            utilisateur.setBornes(bornes);
        }
        if(utilisateurDto.getReservationsId() != null &&
        utilisateurDto.getReservationsId().isEmpty()){
            List<Reservation> reservations = reservationRepository.findAllById(utilisateurDto
                    .getReservationsId());
            utilisateur.setReservations(reservations);
        }
        return utilisateurRepository.save(utilisateur);

    }

    @Override
    public List<UtilisateurDto> getAllUtilisateurs(){
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        return utilisateurs.stream().map(utilisateurMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UtilisateurDto getUtilisateurById(Long id){
        return utilisateurRepository
                .findById(id).map(utilisateurMapper::toDto)
                .orElse(null);
    }

    @Override
    public void deleteUtilisateur(Long id){
        utilisateurRepository.deleteById(id);
    }
}
