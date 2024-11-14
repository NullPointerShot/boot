package com.example.onlinecourseplatform.service.baseService;

import com.example.onlinecourseplatform.converter.CourseMapper;
import com.example.onlinecourseplatform.dto.baseDTO.CourseDTO;
import com.example.onlinecourseplatform.model.baseEntity.Company;
import com.example.onlinecourseplatform.model.baseEntity.Course;
import com.example.onlinecourseplatform.repository.CompanyRepository;
import com.example.onlinecourseplatform.repository.CourseRepository;
import com.example.onlinecourseplatform.service.BaseService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseService extends BaseService<Course, UUID, CourseDTO> {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private CompanyRepository companyRepository;

    protected CourseService (CourseRepository courseRepository, CourseMapper courseMapper, CompanyRepository companyRepository) {
        super(courseRepository, courseMapper);
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.companyRepository = companyRepository;
    }

    public Optional<CourseDTO> findByName(String name){
        return courseRepository.findByTitle(name)
                .map(entityMapper::toDTO);
    }

}
