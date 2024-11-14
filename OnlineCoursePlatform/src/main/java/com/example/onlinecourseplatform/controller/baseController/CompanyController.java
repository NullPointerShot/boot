package com.example.onlinecourseplatform.controller.baseController;

import com.example.onlinecourseplatform.controller.BaseController;
import com.example.onlinecourseplatform.dto.baseDTO.CompanyDTO;
import com.example.onlinecourseplatform.exception.ResourceNotFoundException;
import com.example.onlinecourseplatform.model.baseEntity.Company;
import com.example.onlinecourseplatform.service.baseService.CompanyService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/companies")
public class CompanyController extends BaseController<Company, UUID, CompanyDTO> {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        super(companyService);
        this.companyService = companyService;
    }

    
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY_REP')")
    @PostMapping("/create")
    public ResponseEntity<CompanyDTO> create(@RequestBody CompanyDTO dto) {
        return super.create(dto);
    }

    
    @GetMapping
    @PreAuthorize("permitAll")
    @Override
    public ResponseEntity<List<CompanyDTO>> getAll(Pageable pageable) {
        return super.getAll(pageable);
    }

    
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY_REP')")
    @Override
    public ResponseEntity<CompanyDTO> getById(@PathVariable UUID id) {
        return super.getById(id);
    }

    
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY_REP')")
    @Override
    public ResponseEntity<CompanyDTO> update(@PathVariable UUID id, @RequestBody CompanyDTO dto) throws ResourceNotFoundException {
        return super.update(id, dto);
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return super.delete(id);
    }
}
