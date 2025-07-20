package com.example.BC_Revision.service;

import com.example.BC_Revision.dto.LieuDto;
import com.example.BC_Revision.model.Lieu;

import java.util.List;
import java.util.Optional;

public interface LieuService {

    Lieu saveLieu(LieuDto lieuDto);

    List<LieuDto> getAllLieux();

    LieuDto getLieuById(Long id);

    void deleteLieu(Long id);

    List<String> getAllVilles();
}
