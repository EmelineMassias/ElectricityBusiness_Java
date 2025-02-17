package com.example.BC_Revision.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LieuDto {
    Long id;
    private String nomRue;
    private String codePostal;
    private String ville;
    private List<Long> bornesId;


}
