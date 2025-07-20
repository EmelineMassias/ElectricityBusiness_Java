package com.example.BC_Revision.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BorneSearchCriteriaDto {
    private String ville;
    private LocalDate fromDate;
    private LocalDate toDate;
    private LocalTime startTime;
    private LocalTime endTime;
}