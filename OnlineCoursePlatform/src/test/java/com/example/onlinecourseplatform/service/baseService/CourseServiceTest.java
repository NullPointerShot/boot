package com.example.onlinecourseplatform.service.baseService;

import com.example.onlinecourseplatform.converter.CourseMapper;
import com.example.onlinecourseplatform.dto.baseDTO.CourseDTO;
import com.example.onlinecourseplatform.exception.ResourceNotFoundException;
import com.example.onlinecourseplatform.model.baseEntity.Course;
import com.example.onlinecourseplatform.repository.CompanyRepository;
import com.example.onlinecourseplatform.repository.CourseRepository;
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

class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private CompanyRepository companyRepository;

    private UUID courseId;
    private Course course;
    private CourseDTO courseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courseId = UUID.randomUUID();
        course = new Course();
        course.setId(courseId);
        courseDTO = new CourseDTO();
        courseDTO.setId(courseId);
    }

    @Test
    void testFindAll() {
        
        Page<Course> coursesPage = new PageImpl<>(List.of(course));
        when(courseRepository.findAll(any(Pageable.class))).thenReturn(coursesPage);
        when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        
        Page<CourseDTO> result = courseService.findAll(Pageable.unpaged());

        
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(courseRepository).findAll(any(Pageable.class));
    }

    @Test
    void testFindById_Success() {
        
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        
        Optional<CourseDTO> foundCourseDTO = courseService.findById(courseId);

        
        assertTrue(foundCourseDTO.isPresent());
        assertEquals(courseId, foundCourseDTO.get().getId());
        verify(courseRepository).findById(courseId);
    }

    @Test
    void testFindById_NotFound() {
        
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        
        Optional<CourseDTO> foundCourseDTO = courseService.findById(courseId);

        
        assertFalse(foundCourseDTO.isPresent());
        verify(courseRepository).findById(courseId);
    }

    @Test
    void testSave() {
        
        when(courseMapper.toEntity(courseDTO)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);
        when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        
        CourseDTO savedCourseDTO = courseService.save(courseDTO);

        
        assertNotNull(savedCourseDTO);
        assertEquals(courseId, savedCourseDTO.getId());
        verify(courseRepository).save(course);
    }

    @Test
    void testUpdate_Success() {
        
        when(courseRepository.existsById(courseId)).thenReturn(true);
        when(courseMapper.toEntity(courseDTO)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);
        when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        
        CourseDTO updatedCourseDTO = courseService.update(courseId, courseDTO);

        
        assertNotNull(updatedCourseDTO);
        assertEquals(courseId, updatedCourseDTO.getId());
        verify(courseRepository).save(course);
    }

    @Test
    void testUpdate_NotFound() {
        
        when(courseRepository.existsById(courseId)).thenReturn(false);

        
        assertThrows(ResourceNotFoundException.class, () -> courseService.update(courseId, courseDTO));
        verify(courseRepository, never()).save(any());
    }

    @Test
    void testDeleteById_Success() {
        
        when(courseRepository.existsById(courseId)).thenReturn(true);

        
        courseService.deleteById(courseId);

        
        verify(courseRepository).deleteById(courseId);
    }

    @Test
    void testDeleteById_NotFound() {
        
        when(courseRepository.existsById(courseId)).thenReturn(false);

        
        assertThrows(ResourceNotFoundException.class, () -> courseService.deleteById(courseId));
        verify(courseRepository, never()).deleteById(courseId);
    }

    @Test
    void testFindByName_Success() {
        String courseName = "Test Course";
        course.setTitle(courseName);
        courseDTO.setTitle(courseName);

        
        when(courseRepository.findByTitle(courseName)).thenReturn(Optional.of(course));
        when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        
        Optional<CourseDTO> foundCourseDTO = courseService.findByName(courseName);

        
        assertTrue(foundCourseDTO.isPresent());
        assertEquals(courseName, foundCourseDTO.get().getTitle());
        verify(courseRepository).findByTitle(courseName);
    }

    @Test
    void testFindByName_NotFound() {
        String courseName = "Non-existent Course";

        
        when(courseRepository.findByTitle(courseName)).thenReturn(Optional.empty());

        
        Optional<CourseDTO> foundCourseDTO = courseService.findByName(courseName);

        
        assertFalse(foundCourseDTO.isPresent());
        verify(courseRepository).findByTitle(courseName);
    }
}
