package com.bijianhua.guli.service.edu.controller.api;

import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.service.edu.entity.Course;
import com.bijianhua.guli.service.edu.entity.Teacher;
import com.bijianhua.guli.service.edu.service.CourseService;
import com.bijianhua.guli.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author bijianhua
 * @since 2023/3/17
 */

@CrossOrigin
@Slf4j
@RestController
@Api(tags = "首页")
@RequestMapping("api/edu/index")
public class ApiIndexController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;

    @ApiOperation("获取首页课程和讲师数据")
    @GetMapping
    private R index() {
        //查询热门课程
        List<Course> courseList = courseService.selectHotCourse();
        //查询热门讲师
        List<Teacher> teacherList = teacherService.selectHotTeacher();

        return R.ok().data("courseList", courseList).data("teacherList", teacherList);

    }
}
