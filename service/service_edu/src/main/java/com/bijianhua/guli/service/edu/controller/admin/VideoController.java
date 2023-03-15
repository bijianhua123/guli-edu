package com.bijianhua.guli.service.edu.controller.admin;


import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.service.edu.entity.Video;
import com.bijianhua.guli.service.edu.entity.vo.VideoVo;
import com.bijianhua.guli.service.edu.feign.VdoFileService;
import com.bijianhua.guli.service.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-08
 */
@CrossOrigin
@Slf4j
@Api(tags = "课程视频管理")
@RestController
@RequestMapping("admin/edu/video")
public class VideoController {

    @Autowired
    private VideoService videoService;


    @ApiOperation("保存课时信息")
    @PostMapping("save")
    public R saveVideo(
            @ApiParam(value = "课时信息", required = true)
            @RequestBody Video video) {
        boolean result = videoService.save(video);
        if (!result) {
            return R.error().message("保存失败");
        }
        return R.ok().message("保存成功");
    }

    @ApiOperation("更新课时信息")
    @PutMapping("update")
    public R updateVideo(
            @ApiParam(value = "课时信息", required = true)
            @RequestBody Video video) {
        boolean result = videoService.updateById(video);
        if (!result) {
            return R.error().message("保存失败");
        }
        return R.ok().message("保存成功");
    }

    @ApiOperation("删除课时信息")
    @DeleteMapping("remove/{id}")
    public R deleteVideo(
            @ApiParam(value = "课时id", required = true)
            @PathVariable String id) {


        //时间课时id去删除视频信息
        videoService.removeMediaVideoById(id);

        boolean result = videoService.removeById(id);
        if (!result) {
            return R.error().message("删除失败");
        }
        return R.ok().message("删除成功");
    }

    @ApiOperation("根据id查询课时")
    @GetMapping("get/{id}")
    public R getById(
            @ApiParam(value = "课时id", required = true)
            @PathVariable String id) {

        Video video = videoService.getById(id);
        if (video != null) {
            return R.ok().data("item", video);
        } else {
            return R.error().message("数据不存在");
        }
    }

}

