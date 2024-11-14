package com.example.onlinecourseplatform.converter;

import com.example.onlinecourseplatform.dto.baseDTO.CompanyDTO;
import com.example.onlinecourseplatform.model.baseEntity.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface CompanyMapper extends EntityMapper<Company, CompanyDTO> {
}
