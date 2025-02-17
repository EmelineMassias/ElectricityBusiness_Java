package com.example.BC_Revision.service.impl;

import com.example.BC_Revision.dto.LieuDto;
import com.example.BC_Revision.mapper.LieuMapper;
import com.example.BC_Revision.model.Borne;
import com.example.BC_Revision.model.Lieu;
import com.example.BC_Revision.repository.BorneRepository;
import com.example.BC_Revision.repository.LieuRepository;
import com.example.BC_Revision.service.LieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LieuServiceImpl implements LieuService {

    @Autowired

    private LieuMapper lieuMapper;
    private LieuDto lieuDto;
    private LieuRepository lieuRepository;

    private BorneRepository borneRepository;

    public LieuServiceImpl( LieuRepository lieuRepository, BorneRepository borneRepository
                            ){
        this.lieuRepository = lieuRepository;
        this.borneRepository = borneRepository;
    }

    @Override
    public Lieu saveLieu(LieuDto lieuDto) {
        Lieu lieu = lieuMapper.toEntity(lieuDto);
        if(lieuDto.getBornesId() !=null && lieuDto.getBornesId().isEmpty()) {
            List<Borne> bornes = borneRepository.findAllById(lieuDto.getBornesId());
            lieu.setBornes(bornes);
        }
        return lieuRepository.save(lieu);
    }

    @Override
    public List<LieuDto> getAllLieux() {
        List<Lieu> lieux = lieuRepository.findAll();
        return lieux.stream().map(lieuMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public LieuDto getLieuById(Long id){
        return lieuRepository
                .findById(id).map(lieuMapper::toDto)
                .orElse(null);

    }

    @Override
    public void deleteLieu(Long id) {
        lieuRepository.deleteById(id);

    }

}
