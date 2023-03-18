package com.bijianhua.guli.service.cms.controller.api;

import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.service.cms.entity.Ad;
import com.bijianhua.guli.service.cms.service.AdService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author bijianhua
 * @since 2023/3/17
 */

@Api(tags = "广告推荐")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("api/cms/ad")
public class ApiAdController {

    @Autowired
    private AdService adService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation("根据推荐位id显示广告推荐")
    @GetMapping("list/{adTypeId}")
    public R listByAdTypeId(@ApiParam(value = "推荐位id", required = true) @PathVariable String adTypeId) {

        List<Ad> ads = adService.selectByAdTypeId(adTypeId);
        return R.ok().data("items", ads);
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    @ApiOperation("test")
    @PostMapping("save")
    public void save(@RequestBody Ad ad) throws JsonProcessingException {
        String json = mapper.writeValueAsString(ad);
        stringRedisTemplate.opsForValue().set("ad", json);
        log.info("保存了:{}", ad);
    }

    @GetMapping("get")
    public void get() throws JsonProcessingException {
        String ad = stringRedisTemplate.opsForValue().get("ad");
        Ad value = mapper.readValue(ad, Ad.class);
        System.out.println(value);
    }

}
