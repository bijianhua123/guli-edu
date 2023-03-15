package com.bijianhua.guli.service.edu.controller.admin;


import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.service.edu.entity.Chapter;
import com.bijianhua.guli.service.edu.entity.vo.ChapterVo;
import com.bijianhua.guli.service.edu.service.ChapterService;
import com.bijianhua.guli.service.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin
@Slf4j
@Api(tags = "章节管理")
@RestController
@RequestMapping("admin/edu/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    /**
     * 注入课时
     */
    @Autowired
    private VideoService videoService;


    @ApiOperation("新增章节")
    @PostMapping("save")
    public R save(
            @ApiParam(value = "章节", required = true)
            @RequestBody Chapter chapter) {
        boolean result = chapterService.save(chapter);
        if (!result) {
            return R.error().message("添加失败");
        }
        return R.ok().message("新增章节成功");
    }

    @ApiOperation("根据id获取章节")
    @GetMapping("get/{id}")
    public R getChapterById(
            @ApiParam(value = "章节Id", required = true)
            @PathVariable String id) {
        Chapter chapter = chapterService.getById(id);
        if (chapter == null) {
            return R.error().message("数据不存在");
        }
        return R.ok().data("item", chapter);
    }

    @ApiOperation("更新章节信息")
    @PutMapping("update")
    public R updateById(
            @ApiParam(value = "章节信息", required = true)
            @RequestBody Chapter chapter) {
        boolean result = chapterService.updateById(chapter);
        if (!result) {
            return R.error().message("更新失败");
        }
        return R.ok().message("更新成功");
    }

    @ApiOperation("根据id删除章节信息")
    @DeleteMapping("remove/{id}")
    public R removeChapterById(
            @ApiParam(value = "章节id", required = true)
            @PathVariable String id) {
        //删除章节下的所有视频信息
        videoService.removeMediaVideoByChapterId(id);
        //删除章节信息
        boolean result = chapterService.removeChapterById(id);
        if (!result) {
            return R.error().message("删除失败");
        }
        return R.ok().message("删除成功");
    }

    @ApiOperation("根据课程id显示章节嵌套列表")
    @GetMapping("nested-list/{courseId}")
    public R nestedListByCourseId(
            @ApiParam(value = "课程id", required = true)
            @PathVariable String courseId) {
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);
        return R.ok().data("items", chapterVoList);
    }



}

