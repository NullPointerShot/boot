package com.example.onlinecourseplatform.controller.baseController;

import com.example.onlinecourseplatform.dto.baseDTO.CompanyDTO;
import com.example.onlinecourseplatform.exception.ResourceNotFoundException;
import com.example.onlinecourseplatform.model.baseEntity.Company;
import com.example.onlinecourseplatform.service.baseService.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CompanyControllerTest {

    @InjectMocks
    private CompanyController companyController;

    @Mock
    private CompanyService companyService;

    private CompanyDTO companyDTO;
    private UUID companyId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        companyId = UUID.randomUUID();
        companyDTO = new CompanyDTO();
        companyDTO.setId(companyId);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "COMPANY_REP"})
    void create_ShouldReturnCreatedCompany() {
        when(companyService.save(any(CompanyDTO.class))).thenReturn(companyDTO);

        ResponseEntity<CompanyDTO> response = companyController.create(companyDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(companyDTO, response.getBody());
        verify(companyService, times(1)).save(companyDTO);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "COMPANY_REP"})
    void getAll_ShouldReturnCompanies() {
        List<CompanyDTO> companies = new ArrayList<>();
        companies.add(companyDTO);
        when(companyService.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(companies));

        ResponseEntity<List<CompanyDTO>> response = companyController.getAll(Pageable.unpaged());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(companies, response.getBody());
        verify(companyService, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "COMPANY_REP"})
    void getById_ShouldReturnCompany_WhenExists() throws ResourceNotFoundException {
        when(companyService.findById(companyId)).thenReturn(Optional.of(companyDTO));

        ResponseEntity<CompanyDTO> response = companyController.getById(companyId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(companyDTO, response.getBody());
        verify(companyService, times(1)).findById(companyId);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "COMPANY_REP"})
    void getById_ShouldReturnNotFound_WhenNotExists() throws ResourceNotFoundException {
        when(companyService.findById(companyId)).thenReturn(Optional.empty());

        ResponseEntity<CompanyDTO> response = companyController.getById(companyId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(companyService, times(1)).findById(companyId);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "COMPANY_REP"})
    void update_ShouldReturnUpdatedCompany() throws ResourceNotFoundException {
        when(companyService.update(any(UUID.class), any(CompanyDTO.class))).thenReturn(companyDTO);

        ResponseEntity<CompanyDTO> response = companyController.update(companyId, companyDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(companyDTO, response.getBody());
        verify(companyService, times(1)).update(companyId, companyDTO);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete_ShouldReturnNoContent() {
        ResponseEntity<Void> response = companyController.delete(companyId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(companyService, times(1)).deleteById(companyId);
    }
}
