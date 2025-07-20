package com.example.BC_Revision.service;

import com.example.BC_Revision.dto.UtilisateurDto;
import com.example.BC_Revision.model.Utilisateur;


import java.util.List;
import java.util.Optional;

public interface UtilisateurService {

    Utilisateur saveUtilisateur(
            UtilisateurDto utilisateurDto);

    List<UtilisateurDto> getAllUtilisateurs();

    UtilisateurDto getUtilisateurById(Long id);

    Utilisateur updateUtilisateur(UtilisateurDto utilisateurDto);

    void deleteUtilisateur(Long id);

    UtilisateurDto getUtilisateurByEmail(String email);

}
