package com.bijianhua.guli.service.trade.feign.fallback;

import com.bijianhua.guli.service.base.dto.CourseDto;
import com.bijianhua.guli.service.trade.feign.EduCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author bijianhua
 * @since 2023/3/19
 */
@Service
@Slf4j
public class EduCourseServiceFallBack implements EduCourseService {
    @Override
    public CourseDto getCourseDtoById(String courseId) {
        log.error("熔断保护");
        return null;
    }
}
