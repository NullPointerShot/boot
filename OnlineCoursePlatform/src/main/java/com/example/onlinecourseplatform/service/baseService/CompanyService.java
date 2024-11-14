package com.example.onlinecourseplatform.service.baseService;

import com.example.onlinecourseplatform.converter.CompanyMapper;
import com.example.onlinecourseplatform.dto.baseDTO.CompanyDTO;
import com.example.onlinecourseplatform.model.baseEntity.Company;
import com.example.onlinecourseplatform.repository.CompanyRepository;
import com.example.onlinecourseplatform.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompanyService extends BaseService<Company, UUID, CompanyDTO> {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Autowired
    protected CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        super(companyRepository, companyMapper);
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }


}
