package com.bijianhua.guli.service.edu.feign.fallback;

import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.service.edu.feign.OssFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 远程服务接口调用失败备选方案
 */
@Service
@Slf4j
public class OssFileServiceFallBack implements OssFileService {
    @Override
    public R test() {
        return R.error();
    }

    @Override
    public R removeAvatarById(String url) {
        log.info("熔断保护");
        return R.error();
    }
}
