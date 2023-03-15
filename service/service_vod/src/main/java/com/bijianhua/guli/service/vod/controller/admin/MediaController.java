package com.bijianhua.guli.service.vod.controller.admin;


import com.aliyuncs.exceptions.ClientException;
import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.common.base.result.ResultCodeEnum;
import com.bijianhua.guli.common.base.util.ExceptionUtils;
import com.bijianhua.guli.service.base.exception.GuliException;
import com.bijianhua.guli.service.vod.service.VideoService;
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


@Slf4j
@CrossOrigin
@Api(tags = "阿里云视频管理")
@RestController
@RequestMapping("admin/vod/media")
public class MediaController {

    @Autowired
    private VideoService videoService;

    @ApiOperation("上传视频")
    @PostMapping("upload")
    public R uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {

        try {
            //获取文件原始名称
            String originalFilename = file.getOriginalFilename();
            //获取文件输入流
            InputStream inputStream = file.getInputStream();
            //调用上传
            String videoId = videoService.uploadVideo(inputStream, originalFilename);
            return R.ok().message("视频上传成功").data("videoId", videoId);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new GuliException(ResultCodeEnum.VIDEO_UPLOAD_TOMCAT_ERROR);
        }
    }

    /**
     * 删除单个视频
     *
     * @param videoId
     * @return
     */
    @ApiOperation("根据视频id删除视频")
    @DeleteMapping("remove/{videoId}")
    public R removeVideoById(
            @ApiParam(value = "视频文件Id", required = true)
            @PathVariable String videoId) {
        try {
            videoService.removeVideoById(videoId);
            return R.ok().message("视频删除成功");
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new GuliException(ResultCodeEnum.VIDEO_DELETE_ALIYUN_ERROR);
        }
    }

    /**
     * 批量删除视频
     *
     * @param videoIdList
     * @return
     */
    @ApiOperation("根据视频id集合删除视频")
    @DeleteMapping("remove")
    public R removeVideoByIdList(
            @ApiParam(value = "视频id集合", required = true)
            @RequestBody List<String> videoIdList) {
        try {
            videoService.removeVideoByIdList(videoIdList);
            return R.ok().message("删除视频成功");
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new GuliException(ResultCodeEnum.VIDEO_DELETE_ALIYUN_ERROR);
        }

    }
}

