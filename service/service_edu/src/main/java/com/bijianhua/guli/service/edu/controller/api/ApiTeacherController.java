package com.bijianhua.guli.service.edu.controller.api;

import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.service.edu.entity.Teacher;
import com.bijianhua.guli.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author bijianhua
 * @since 2023/3/15
 */
@CrossOrigin
@Api(tags = "讲师")
@RestController
@RequestMapping("/api/edu/teacher")
public class ApiTeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("获取讲师列表")
    @GetMapping("list")
    private R listAll() {
        List<Teacher> list = teacherService.list();
        return R.ok().data("items", list).message("获取讲师列表成功");
    }

    @ApiOperation("讲师ID")
    @GetMapping("get/{teacherId}")
    public R getTeacherAndCourse(
            @ApiParam(value = "讲师Id", required = true)
            @PathVariable String teacherId) {

        Map<String, Object> map = teacherService.getTeacherAndCourseById(teacherId);
        return R.ok().data(map);
    }

}
