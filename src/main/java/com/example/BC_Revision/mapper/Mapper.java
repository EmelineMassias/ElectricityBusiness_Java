package com.example.BC_Revision.mapper;

import org.mapstruct.ReportingPolicy;

public @interface Mapper {
    String componentModel();

    ReportingPolicy unmappedTargetPolicy();
}
