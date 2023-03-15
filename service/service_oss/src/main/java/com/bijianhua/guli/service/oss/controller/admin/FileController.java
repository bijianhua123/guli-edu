package com.bijianhua.guli.service.oss.controller.admin;


import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.common.base.result.ResultCodeEnum;
import com.bijianhua.guli.common.base.util.ExceptionUtils;
import com.bijianhua.guli.service.base.exception.GuliException;
import com.bijianhua.guli.service.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Slf4j
@CrossOrigin
@Api(tags = "阿里云文件管理")
@RestController
@RequestMapping("admin/oss/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("upload")
    public R upload(
            @ApiParam(value = "文件", required = true) @RequestParam("file") MultipartFile file,
            @ApiParam(value = "上传的目录", required = true) @RequestParam("module") String module) {

        try {
            String originalFilename = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            String uploadUrl = fileService.fileUpload(inputStream, module, originalFilename);

            return R.ok().message("文件上传成功").data("url", uploadUrl);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new GuliException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }
    }

    @ApiOperation(value = "根据url删除文件")
    @DeleteMapping("remove")
    public R removeFile(
            @ApiParam(value = "要删除的文件url地址", required = true)
            @RequestBody String url) {
        fileService.removeFile(url);
        return R.ok().message("文件删除成功");
    }


    @ApiOperation(value = "测试")
    @GetMapping("test")
    public R test() {
        log.info("oss test被调用");

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return R.ok();
    }


}
