package com.bijianhua.guli.service.edu.controller.api;

import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.service.base.dto.CourseDto;
import com.bijianhua.guli.service.edu.entity.Course;
import com.bijianhua.guli.service.edu.entity.vo.ChapterVo;
import com.bijianhua.guli.service.edu.entity.vo.WebCourseQueryVo;
import com.bijianhua.guli.service.edu.entity.vo.WebCourseVo;
import com.bijianhua.guli.service.edu.service.ChapterService;
import com.bijianhua.guli.service.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author bijianhua
 * @since 2023/3/16
 */

@CrossOrigin
@Api(tags = "课程相关")
@RestController
@RequestMapping("/api/edu/course")
public class ApiCourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @ApiOperation("课程条件查询")
    @GetMapping("list")
    public R pageList(
            @ApiParam(value = "查询条件", required = true)
            WebCourseQueryVo webCourseQueryVo) {

        List<Course> courseList = courseService.webSelectList(webCourseQueryVo);
        return R.ok().data("courseList", courseList);
    }

    @ApiOperation("根据课程Id查询课程详情")
    @GetMapping("get/{courseId}")
    public R selectWebCourseVoById(
            @ApiParam(value = "课程id", required = true)
            @PathVariable String courseId) {

        //查询课程信息和讲师信息
        WebCourseVo webCourseVo = courseService.selectWebCourseVoById(courseId);
        List<ChapterVo> chapterVos = chapterService.nestedList(courseId);
        return R.ok().data("course", webCourseVo).data("chapterVos", chapterVos);
    }

    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("inner/get-course-dto/{courseId}")
    public CourseDto getCourseDtoById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable String courseId) {
        CourseDto courseDto = courseService.getCourseDtoById(courseId);
        return courseDto;
    }

}
