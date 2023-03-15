package com.bijianhua.guli.service.edu.feign.fallback;

import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.service.edu.feign.VdoFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class VdoFileServiceFallBack implements VdoFileService {
    @Override
    public R removeMediaVideoById(String videoId) {
        log.error("启动熔断保护");
        return R.error();
    }
}
