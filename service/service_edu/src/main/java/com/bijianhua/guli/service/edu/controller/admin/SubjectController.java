package com.bijianhua.guli.service.edu.controller.admin;


import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.common.base.result.ResultCodeEnum;
import com.bijianhua.guli.common.base.util.ExceptionUtils;
import com.bijianhua.guli.service.base.exception.GuliException;
import com.bijianhua.guli.service.edu.entity.vo.SubjectVo;
import com.bijianhua.guli.service.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-08
 */
@CrossOrigin //允许跨域
@RestController
@RequestMapping("admin/edu/subject")
@Api(tags = "课程分类管理")
@Slf4j
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation("Excel文件批量导入")
    @PostMapping("import")
    public R batchImport(
            @ApiParam("Excel文件")
            @RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            subjectService.batchImport(inputStream);
            return R.ok().message("Excel文件上传成功");
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new GuliException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
    }

    @ApiOperation("获取课程类别嵌套列表")
    @GetMapping("nested-list")
    public R nestedList() {
        List<SubjectVo> children = subjectService.nestedList();
        return R.ok().data("items", children);
    }
}

