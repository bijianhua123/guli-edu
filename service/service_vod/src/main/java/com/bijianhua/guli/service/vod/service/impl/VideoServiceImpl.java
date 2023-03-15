package com.bijianhua.guli.service.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.bijianhua.guli.common.base.result.ResultCodeEnum;
import com.bijianhua.guli.service.base.exception.GuliException;
import com.bijianhua.guli.service.vod.service.VideoService;
import com.bijianhua.guli.service.vod.util.AliyunClientInitUtil;
import com.bijianhua.guli.service.vod.util.VodProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service
@Slf4j
public class VideoServiceImpl implements VideoService {


    @Autowired
    private VodProperties vodProperties;

    @Override
    public String uploadVideo(InputStream inputStream, String fileOriginalName) {

        String title = fileOriginalName.substring(0, fileOriginalName.lastIndexOf("."));

        UploadStreamRequest uploadStreamRequest = new UploadStreamRequest(
                vodProperties.getKeyid(),
                vodProperties.getKeysecret(),
                title, fileOriginalName, inputStream
        );
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(uploadStreamRequest);

        String videoId = response.getVideoId();
        if (StringUtils.isEmpty(videoId)) {
            log.error("阿里云上传失败：" + response.getCode() + " - " + response.getMessage());
            throw new GuliException(ResultCodeEnum.VIDEO_UPLOAD_ALIYUN_ERROR);
        }
        return videoId;
    }

    @Override
    public void removeVideoById(String videoSourceId) throws Exception {

        DefaultAcsClient client = AliyunClientInitUtil.initVodClient(vodProperties.getKeyid(), vodProperties.getKeysecret());

        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(videoSourceId);
        //执行删除
        client.getAcsResponse(request);
    }


}
