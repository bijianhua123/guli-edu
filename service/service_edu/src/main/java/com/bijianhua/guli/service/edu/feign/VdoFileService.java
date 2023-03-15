package com.bijianhua.guli.service.edu.feign;


import com.bijianhua.guli.common.base.result.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 注册中心远程调用
 * 调用VOD服务
 */
@FeignClient(value = "service-vod")
public interface VdoFileService {

    /**
     * 调用vod服务接口删除视频信息
     *
     * @param videoId 视频id
     */
    @DeleteMapping("admin/vod/media/remove/{videoId}")
    R removeMediaVideoById(@PathVariable("videoId") String videoId);

    /**
     * 调用VOD服务接口批量删除视频信息
     *
     * @param videoIdList 视频id集合
     * @return
     */
    @DeleteMapping("admin/vod/media/remove")
    R removeVideoByIdList(@RequestBody List<String> videoIdList);
}
