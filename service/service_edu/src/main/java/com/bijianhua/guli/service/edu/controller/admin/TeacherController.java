package com.bijianhua.guli.service.edu.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.service.edu.entity.Teacher;
import com.bijianhua.guli.service.edu.entity.vo.TeacherQueryVo;
import com.bijianhua.guli.service.edu.feign.OssFileService;
import com.bijianhua.guli.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-08
 */
@CrossOrigin
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/admin/edu/teacher")
public class TeacherController {

    /**
     * 讲师实现类
     */
    @Autowired
    private TeacherService teacherService;

    /**
     * 服务远程调用
     */
    @Autowired
    private OssFileService ossFileService;

    @ApiOperation("讲师列表")
    @GetMapping("list")
    public R listAll() {
        List<Teacher> list = teacherService.list();
        return R.ok().data("items", list);
    }

    @ApiOperation("根据id删除讲师")
    @DeleteMapping("remove/{id}")
    public R removeTeacher(@PathVariable String id) {
        //删除讲师头像
        boolean b = teacherService.removeTeacherAvatarById(id);

        boolean success = teacherService.removeById(id);
        if (!success) {
            return R.error().message("删除失败");
        }
        return R.ok().message("删除成功");
    }

    @ApiOperation("讲师分页表")
    @GetMapping("list/{page}/{limit}")
    public R listPage(@ApiParam("当前页码") @PathVariable Long page,
                      @ApiParam("每页记录数") @PathVariable Long limit,
                      @ApiParam("条件对象") TeacherQueryVo teacherQuery) {

        Page<Teacher> objectPage = new Page<>(page, limit);
        IPage<Teacher> iPage = teacherService.selectPage(objectPage, teacherQuery);
        //每页记录数
        List<Teacher> records = objectPage.getRecords();
        //总记录数
        long total = objectPage.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation("添加讲师")
    @PostMapping("save")
    public R save(@ApiParam("讲师对象") @RequestBody Teacher teacher) {
        boolean save = teacherService.save(teacher);
        if (!save) {
            return R.error().message("添加失败");
        }
        return R.ok().message("添加成功");
    }

    @ApiOperation("更新讲师")
    @PutMapping("update")
    public R update(@ApiParam("讲师对象") @RequestBody Teacher teacher) {
        boolean update = teacherService.updateById(teacher);
        if (!update) {
            return R.error().message("更新失败");
        }
        return R.ok().message("更新成功");
    }

    @ApiOperation("根据id查询")
    @GetMapping("get/{id}")
    public R getById(@ApiParam("讲师id") @PathVariable String id) {
        Teacher teacher = teacherService.getById(id);
        if (teacher == null) {
            return R.error().message("数据不存在");
        }
        return R.ok().data("item", teacher);
    }


    @ApiOperation("根据id列表批量删除讲师")
    @DeleteMapping("batch-remove")
    public R removeRows(
            @ApiParam(value = "讲师id列表", required = true)
            @RequestBody List<String> idList) {
        boolean success = teacherService.removeByIds(idList);
        if (!success) {
            return R.error().message("批量删除失败");
        }
        return R.ok().message("批量删除成功");
    }

    @ApiOperation("根据左关键字查询讲师名列表")
    @GetMapping("list/name/{key}")
    public R selectNameList(
            @ApiParam(value = "左侧关键字列表")
            @PathVariable String key) {
        List<Map<String, Object>> nmaeList = teacherService.selectNameList(key);
        return R.ok().data("nameList", nmaeList);
    }




    @ApiOperation("测试并发")
    @GetMapping("test_concurrent")
    public R testConcurrent() {
        System.out.println("test_concurrent");
        return R.ok();
    }

    @GetMapping("/message1")
    public String message1() {
        return "message1";
    }

    @GetMapping("/message2")
    public String message2() {
        return "message2";
    }
}

