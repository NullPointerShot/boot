package com.example.onlinecourseplatform.controller.baseController;

import com.example.onlinecourseplatform.controller.BaseController;
import com.example.onlinecourseplatform.dto.baseDTO.CourseDTO;
import com.example.onlinecourseplatform.exception.ResourceNotFoundException;
import com.example.onlinecourseplatform.model.baseEntity.Course;
import com.example.onlinecourseplatform.service.baseService.CourseService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController extends BaseController<Course, UUID, CourseDTO> {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        super(courseService);
        this.courseService = courseService;
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CourseDTO> findByName(@PathVariable String name) {
        Optional<CourseDTO> courseDTO = courseService.findByName(name);
        return courseDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    @PreAuthorize("hasRole('ADMIN') or hasRole('COMPANY_REP')")
    @PostMapping("/create")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        CourseDTO savedCourse = courseService.save(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }

    
    @PreAuthorize("hasRole('ADMIN') or hasRole('COMPANY_REP')")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<CourseDTO> update(@PathVariable UUID id, @RequestBody CourseDTO dto) throws ResourceNotFoundException {
        return super.update(id, dto);
    }

    
    @PreAuthorize("hasRole('ADMIN') or hasRole('COMPANY_REP')")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return super.delete(id);
    }

    
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('COMPANY_REP') or hasRole('ADMIN')")
    public ResponseEntity<List<CourseDTO>> getAllCourses(Pageable pageable) {
        return super.getAll(pageable);
    }

    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('COMPANY_REP') or hasRole('ADMIN')")
    @Override
    public ResponseEntity<CourseDTO> getById(@PathVariable UUID id) {
        return super.getById(id);
    }
}
