package com.bijianhua.guli.service.vod.controller.api;

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

/**
 * @author bijianhua
 * @since 2023/3/16
 */

@CrossOrigin
@Slf4j
@Api(tags = "阿里云视频点播")
@RestController
@RequestMapping("api/vod/media")
public class ApiMediaController {

    @Autowired
    private VideoService videoService;


    @ApiOperation("根据课时Id获取阿里云播放凭证")
    @GetMapping("get-play-auth/{videoId}")
    public R getPlayAuth(
            @ApiParam(value = "课时Id", required = true)
            @PathVariable String videoId) {
        try {
            String playAuthId = videoService.getPlayAuth(videoId);
            return R.ok().data("url", playAuthId);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new GuliException(ResultCodeEnum.FETCH_PLAYAUTH_ERROR);
        }
    }
}
