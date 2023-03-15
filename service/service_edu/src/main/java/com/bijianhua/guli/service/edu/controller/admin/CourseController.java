package com.bijianhua.guli.service.edu.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.service.edu.entity.Course;
import com.bijianhua.guli.service.edu.entity.form.CourseInfoForm;
import com.bijianhua.guli.service.edu.entity.vo.CoursePublishVo;
import com.bijianhua.guli.service.edu.entity.vo.CourseQueryVo;
import com.bijianhua.guli.service.edu.entity.vo.CourseVo;
import com.bijianhua.guli.service.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-08
 */
@CrossOrigin //允许跨域
@RestController
@RequestMapping("admin/edu/course")
@Api(tags = "课程管理")
@Slf4j
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation("新增课程")
    @PostMapping("save-course-info")
    public R saveCourseInfo(
            @ApiParam(value = "课程基本信息", required = true)
            @RequestBody CourseInfoForm courseInfo) {
        String id = courseService.saveCourseInfo(courseInfo);
        return R.ok().data("id", id).message("保存课程信息成功");
    }

    @ApiOperation("根据id获取课程信息")
    @GetMapping("course-info/{id}")
    public R getCourseById(
            @ApiParam(value = "课程id", required = true)
            @PathVariable String id) {
        CourseInfoForm courseInfoForm = courseService.getCourseFromInfoById(id);
        if (courseInfoForm == null) {
            return R.ok().message("数据不存在");
        }
        return R.ok().data("item", courseInfoForm);
    }

    @ApiOperation("更新课程信息")
    @PutMapping("update-course-info")
    public R getCourseById(
            @ApiParam(value = "课程信息对象", required = true)
            @RequestBody CourseInfoForm courseInfoForm) {
        courseService.updateCourseInfoById(courseInfoForm);
        return R.ok().message("更新成功");
    }

    @ApiOperation("课程信息分页展示")
    @GetMapping("list/{page}/{limit}")
    public R pageList(
            @ApiParam(value = "当前页", required = true) @PathVariable Long page,
            @ApiParam(value = "每页显示条数", required = true) @PathVariable Long limit,
            @ApiParam(value = "查询条件对象") CourseQueryVo courseQueryVo) {

        IPage<CourseVo> iPage = courseService.selectPage(page, limit, courseQueryVo);
        //每页记录数
        List<CourseVo> courseVoList = iPage.getRecords();
        //总记录数
        long total = iPage.getTotal();
        return R.ok().data("total", total).data("rows", courseVoList);

    }

    @ApiOperation("根据id删除课程")
    @DeleteMapping("remove/{id}")
    public R removeCourseById(
            @ApiParam(value = "课程id", required = true)
            @PathVariable String id) {
        //TODO 删除课程关联视频 需要通过openFeign调用Oss中的服务 基于nacos


        //TODO 删除课程封面 需要通过openFeign调用Oss中的服务 基于nacos
        boolean b = courseService.removeCoverById(id);

        // TODO 删除课程信息
        boolean success = courseService.removeCourseById(id);
        if (!success) {
            return R.error().message("删除失败");
        }
        return R.ok().message("删除成功");
    }

    @ApiOperation("根据ID获取课程发布信息")
    @GetMapping("course-publish-info/{id}")
    public R getCoursePublishVoById(
            @ApiParam(value = "课程Id", required = true)
            @PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVoById(id);
        if (coursePublishVo == null) {
            return R.error().message("数据不存在");
        }
        return R.ok().data("item", coursePublishVo);
    }

    @ApiOperation("根据课程id发布课程")
    @PutMapping("publish-course/{id}")
    public R publishCourseById(
            @ApiParam(value = "课程id", required = true)
            @PathVariable String id) {

        boolean success = courseService.publishCourseById(id);
        if (!success) {
            return R.error().message("发布失败");
        }
        return R.ok().message("发布成功");
    }

}

