package com.bijianhua.guli.service.edu.feign;


import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.service.edu.feign.fallback.OssFileServiceFallBack;
import com.bijianhua.guli.service.edu.feign.fallback.OssFileServiceFallBack;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 注册中心远程调用
 */
@FeignClient(value = "service-oss", fallback = OssFileServiceFallBack.class)
public interface OssFileService {




    /**
     * 调用service-oss服务删除图片
     *
     * @param url 图片url或id
     * @return
     */
    @DeleteMapping("admin/oss/file/remove")
    R removeAvatarById(String url);
}
