package com.example.BC_Revision.dto;

import com.example.BC_Revision.model.Lieu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorneDto implements Serializable {
    Long id;
    private String nomBorne;
    private String puissance;
    private Boolean estDisponible;
    private String instruction;
    private Boolean surPied;
    private Double latitude;
    private Double longitude;
    private Float prix;
    private String photo;
    private Long utilisateurId;
    private Long LieuId;
    private List<Long> mediasId;
    private List<Long> reservationsId;


}
