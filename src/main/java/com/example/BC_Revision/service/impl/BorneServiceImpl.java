package com.example.BC_Revision.service.impl;

import com.example.BC_Revision.dto.BorneDto;
import com.example.BC_Revision.mapper.BorneMapper;
import com.example.BC_Revision.model.*;
import com.example.BC_Revision.repository.*;
import com.example.BC_Revision.service.BorneService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class BorneServiceImpl implements BorneService {

    private BorneMapper borneMapper;
    private BorneDto borneDto;
    private BorneRepository borneRepository;
    private UtilisateurRepository utilisateurRepository;
    private LieuRepository lieuRepository;
    private ReservationRepository reservationRepository;
    private MediaRepository mediaRepository;

    public BorneServiceImpl(BorneRepository borneRepository, BorneMapper borneMapper, MediaRepository mediaRepository, UtilisateurRepository utilisateurRepository,
                            LieuRepository lieuRepository, ReservationRepository reservationRepository){
        this.borneRepository = borneRepository;
        this.borneMapper = borneMapper;
        this.mediaRepository = mediaRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.lieuRepository = lieuRepository;
        this.reservationRepository = reservationRepository;
    }


    @Override
    public Borne saveBorne(BorneDto borneDto) {
        Borne borne = borneMapper.toEntity(borneDto);
        if(borneDto.getMediasId() != null &&
                borneDto.getMediasId().isEmpty()) {
            List<Media> medias = mediaRepository
                    .findAllById(borneDto
                            .getMediasId());
            borne.setMedias(medias);
        }
        if(borneDto.getReservationsId() != null && borneDto.getReservationsId().isEmpty()) {
            List<Reservation> reservations = reservationRepository.findAllById(borneDto.getReservationsId());
            borne.setReservations(reservations);
        }
        if(borneDto.getUtilisateurId() != null){
            Utilisateur utilisateur= utilisateurRepository
            .findById(borneDto.getUtilisateurId())
            .orElse(null);
            borne.setUtilisateur(utilisateur);
        }
        if(borneDto.getLieuId() != null){
            Lieu lieu = lieuRepository
            .findById(borneDto.getLieuId())
            .orElse(null);
            borne.setLieu(lieu);
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
                .orElse(null);
    }

    @Override
    public void deleteBorne(Long id) {

        borneRepository.deleteById(id);
    }


}
