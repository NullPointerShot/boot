package com.example.onlinecourseplatform.service.baseService;

import com.example.onlinecourseplatform.converter.CompanyMapper;
import com.example.onlinecourseplatform.dto.baseDTO.CompanyDTO;
import com.example.onlinecourseplatform.exception.ResourceNotFoundException;
import com.example.onlinecourseplatform.model.baseEntity.Company;
import com.example.onlinecourseplatform.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompanyServiceTest {

    @InjectMocks
    private CompanyService companyService;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CompanyMapper companyMapper;

    private UUID companyId;
    private Company company;
    private CompanyDTO companyDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        companyId = UUID.randomUUID();
        company = new Company();
        company.setId(companyId);
        companyDTO = new CompanyDTO();
        companyDTO.setId(companyId);
    }

    @Test
    void testFindAll() {
        
        Page<Company> companiesPage = new PageImpl<>(List.of(company));
        when(companyRepository.findAll(any(Pageable.class))).thenReturn(companiesPage);
        when(companyMapper.toDTO(company)).thenReturn(companyDTO);

        
        Page<CompanyDTO> result = companyService.findAll(Pageable.unpaged());

        
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(companyRepository).findAll(any(Pageable.class));
    }

    @Test
    void testFindById_Success() {
        
        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));
        when(companyMapper.toDTO(company)).thenReturn(companyDTO);

        
        Optional<CompanyDTO> foundCompanyDTO = companyService.findById(companyId);

        
        assertTrue(foundCompanyDTO.isPresent());
        assertEquals(companyId, foundCompanyDTO.get().getId());
        verify(companyRepository).findById(companyId);
    }

    @Test
    void testFindById_NotFound() {
        
        when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

        
        Optional<CompanyDTO> foundCompanyDTO = companyService.findById(companyId);

        
        assertFalse(foundCompanyDTO.isPresent());
        verify(companyRepository).findById(companyId);
    }

    @Test
    void testSave() {
        
        when(companyMapper.toEntity(companyDTO)).thenReturn(company);
        when(companyRepository.save(company)).thenReturn(company);
        when(companyMapper.toDTO(company)).thenReturn(companyDTO);

        
        CompanyDTO savedCompanyDTO = companyService.save(companyDTO);

        
        assertNotNull(savedCompanyDTO);
        assertEquals(companyId, savedCompanyDTO.getId());
        verify(companyRepository).save(company);
    }

    @Test
    void testUpdate_Success() {
        
        when(companyRepository.existsById(companyId)).thenReturn(true);
        when(companyMapper.toEntity(companyDTO)).thenReturn(company);
        when(companyRepository.save(company)).thenReturn(company);
        when(companyMapper.toDTO(company)).thenReturn(companyDTO);

        
        CompanyDTO updatedCompanyDTO = companyService.update(companyId, companyDTO);

        
        assertNotNull(updatedCompanyDTO);
        assertEquals(companyId, updatedCompanyDTO.getId());
        verify(companyRepository).save(company);
    }

    @Test
    void testUpdate_NotFound() {
        
        when(companyRepository.existsById(companyId)).thenReturn(false);

        
        assertThrows(ResourceNotFoundException.class, () -> companyService.update(companyId, companyDTO));
        verify(companyRepository, never()).save(any());
    }

    @Test
    void testDeleteById_Success() {
        
        when(companyRepository.existsById(companyId)).thenReturn(true);

        
        companyService.deleteById(companyId);

        
        verify(companyRepository).deleteById(companyId);
    }

    @Test
    void testDeleteById_NotFound() {
        
        when(companyRepository.existsById(companyId)).thenReturn(false);

        
        assertThrows(ResourceNotFoundException.class, () -> companyService.deleteById(companyId));
        verify(companyRepository, never()).deleteById(companyId);
    }
}
