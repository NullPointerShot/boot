package com.example.onlinecourseplatform.controller.baseController;

import com.example.onlinecourseplatform.dto.baseDTO.CourseDTO;
import com.example.onlinecourseplatform.model.baseEntity.Course;
import com.example.onlinecourseplatform.service.baseService.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CourseControllerTest {

    @InjectMocks
    private CourseController courseController;

    @Mock
    private CourseService courseService;

    private CourseDTO courseDTO;
    private UUID courseId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courseId = UUID.randomUUID();
        courseDTO = new CourseDTO();
        courseDTO.setId(courseId);
        
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateCourse_ShouldReturnUpdatedCourse() {
        when(courseService.update(any(UUID.class), any(CourseDTO.class))).thenReturn(courseDTO);

        ResponseEntity<CourseDTO> response = courseController.update(courseId, courseDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courseDTO, response.getBody());
        verify(courseService, times(1)).update(courseId, courseDTO);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteCourse_ShouldReturnNoContent() {
        ResponseEntity<Void> response = courseController.delete(courseId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(courseService, times(1)).deleteById(courseId);
    }

    @Test
    void findByName_ShouldReturnCourse() {
        when(courseService.findByName("Course Name")).thenReturn(Optional.of(courseDTO));

        ResponseEntity<CourseDTO> response = courseController.findByName("Course Name");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courseDTO, response.getBody());
        verify(courseService, times(1)).findByName("Course Name");
    }

    @Test
    void findByName_ShouldReturnNotFound() {
        when(courseService.findByName("Nonexistent Course")).thenReturn(Optional.empty());

        ResponseEntity<CourseDTO> response = courseController.findByName("Nonexistent Course");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(courseService, times(1)).findByName("Nonexistent Course");
    }
}
