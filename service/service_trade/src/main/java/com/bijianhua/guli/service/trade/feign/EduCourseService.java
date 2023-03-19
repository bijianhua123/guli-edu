package com.bijianhua.guli.service.trade.feign;

import com.bijianhua.guli.service.base.dto.CourseDto;
import com.bijianhua.guli.service.trade.feign.fallback.EduCourseServiceFallBack;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author bijianhua
 * @since 2023/3/19
 */
@Service
@FeignClient(value = "service-edu"/*, fallback = EduCourseServiceFallBack.class*/)
public interface EduCourseService {

    @GetMapping("/api/edu/course/inner/get-course-dto/{courseId}")
    CourseDto getCourseDtoById(@PathVariable(value = "courseId") String courseId);
}
