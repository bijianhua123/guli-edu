package com.bijianhua.guli.service.edu.feign;


import com.bijianhua.guli.common.base.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 注册中心远程调用
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
}
