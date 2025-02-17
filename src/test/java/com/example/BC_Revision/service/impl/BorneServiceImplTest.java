package com.example.BC_Revision.service.impl;

import com.example.BC_Revision.dto.BorneDto;
import com.example.BC_Revision.model.Borne;
import com.example.BC_Revision.model.Lieu;
import com.example.BC_Revision.model.Utilisateur;
import com.example.BC_Revision.repository.BorneRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class BorneServiceImplTest {
    @Mock
    BorneRepository borneRepository;
    @InjectMocks
    BorneServiceImpl borneServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBorne() {
        when(borneRepository.save(any(Borne.class))).thenReturn(new Borne());

        Borne result = borneServiceImpl.saveBorne(new Borne(Long.valueOf(1), "nomBorne", "puissance", Boolean.TRUE, "instruction", Boolean.TRUE, Double.valueOf(0), Double.valueOf(0), Float.valueOf(1.1f), "photo", new Utilisateur(Long.valueOf(1), "nom", "prenom", "email", "motDePasse", "telephone", "role", LocalDate.of(2024, Month.OCTOBER, 15), Integer.valueOf(0), "nomRue", "codePostal", "ville", List.of(null)), new Lieu(Long.valueOf(1), "nomRue", "codePostal", "ville", List.of(null))));
        Assertions.assertEquals(new Borne(Long.valueOf(1), "nomBorne", "puissance", Boolean.TRUE, "instruction", Boolean.TRUE, Double.valueOf(0), Double.valueOf(0), Float.valueOf(1.1f), "photo", new Utilisateur(Long.valueOf(1), "nom", "prenom", "email", "motDePasse", "telephone", "role", LocalDate.of(2024, Month.OCTOBER, 15), Integer.valueOf(0), "nomRue", "codePostal", "ville", List.of(null)), new Lieu(Long.valueOf(1), "nomRue", "codePostal", "ville", List.of(null))), result);
    }

    @Test
    void testGetBorneById() {
        when(borneRepository.findById(any(Long.class))).thenReturn(null);

        BorneDto result = borneServiceImpl.getBorneById(Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testDeleteBorne() {
        borneServiceImpl.deleteBorne(Long.valueOf(1));
        verify(borneRepository).deleteById(any(Long.class));
    }

    @Test
    void testGetAllBornes() {
        when(borneRepository.findAll()).thenReturn(List.of(new Borne()));

        List<BorneDto> result = borneServiceImpl.getAllBornes();
        Assertions.assertEquals(List.of(new Borne(Long.valueOf(1), "nomBorne", "puissance", Boolean.TRUE, "instruction", Boolean.TRUE, Double.valueOf(0), Double.valueOf(0), Float.valueOf(1.1f), "photo", new Utilisateur(Long.valueOf(1), "nom", "prenom", "email", "motDePasse", "telephone", "role", LocalDate.of(2024, Month.OCTOBER, 15), Integer.valueOf(0), "nomRue", "codePostal", "ville", List.of(null)), new Lieu(Long.valueOf(1), "nomRue", "codePostal", "ville", List.of(null)))), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme