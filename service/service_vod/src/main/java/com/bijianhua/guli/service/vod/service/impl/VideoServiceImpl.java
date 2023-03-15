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
import java.util.List;

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

    @Override
    public void removeVideoByIdList(List<String> videoIdList) throws ClientException {
        DefaultAcsClient client = AliyunClientInitUtil.initVodClient(vodProperties.getKeyid(), vodProperties.getKeysecret());
        DeleteVideoRequest request = new DeleteVideoRequest();
        //拼接删除视频的字段
        StringBuffer stringBuffer = new StringBuffer();
        /*阿里云批量删除视频规则
        视频ID列表。
        由一个或多个视频ID组成，多个ID之间使用半角逗号（,）分隔。最多支持20个。视频ID可通过以下方式获取：
        StringBuffer拼接考虑:如果长度大于20个，那么就应该执行一次删除，并且清空buffer
        */
        //如果视频列表为0则不需要删除
        if (videoIdList.isEmpty()) {
            return;
        }
        //获取视频列表id长度
        int size = videoIdList.size();
        for (int i = 0; i < size; i++) {
            stringBuffer.append(videoIdList.get(i));
            //如果i等于size-1就代表以及执行到最后一个添加了 准备执行删除操作,此时不需要逗号
            //如果i取余数等于19则代表以及满了一个20 了
            if (i == size - 1 || i % 20 == 19) {
                log.info("idList=" + stringBuffer);
                //准备删除
                //支持传入多个视频ID，多个用逗号分隔
                request.setVideoIds(stringBuffer.toString());
                //执行删除
                client.getAcsResponse(request);
                //重置stringBuffer
                stringBuffer = new StringBuffer();
            } else if (i % 20 < 19) {
                stringBuffer.append(",");
            }

        }

    }


}
