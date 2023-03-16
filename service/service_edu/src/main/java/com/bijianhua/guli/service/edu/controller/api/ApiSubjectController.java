package com.bijianhua.guli.service.edu.controller.api;

import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.service.edu.entity.vo.SubjectVo;
import com.bijianhua.guli.service.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author bijianhua
 * @since 2023/3/16
 */

@CrossOrigin
@Api(tags = "课程分类")
@RestController
@RequestMapping("/api/edu/subject")
public class ApiSubjectController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation("获取课程嵌套列表")
    @GetMapping("nested-list")
    public R nestedList() {
        List<SubjectVo> subjectVoList = subjectService.nestedList();
        return R.ok().data("subjectList", subjectVoList);
    }
}
