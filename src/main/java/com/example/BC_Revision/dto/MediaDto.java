package com.example.BC_Revision.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaDto {

Long id;
private String libelle;
private String typeMedia;
private Long borneId;

}
