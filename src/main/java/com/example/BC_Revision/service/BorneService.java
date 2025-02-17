package com.example.BC_Revision.service;

import com.example.BC_Revision.dto.BorneDto;
import com.example.BC_Revision.model.Borne;


import java.util.List;
import java.util.Optional;


public interface BorneService {

    Borne saveBorne(BorneDto borneDto);
    List<BorneDto> getAllBornes();

    BorneDto getBorneById(Long id);

    void deleteBorne(Long id);


}
