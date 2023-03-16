package com.bijianhua.guli.service.vod.service;


import com.aliyuncs.exceptions.ClientException;

import java.io.InputStream;
import java.util.List;

/**
 * 视频上传接口
 */
public interface VideoService {

    /**
     * 上传视频到阿里云
     *
     * @param inputStream      视频输入流
     * @param fileOriginalName 视频原始名称
     * @return 视频在阿里云端存储的id
     */
    String uploadVideo(InputStream inputStream, String fileOriginalName);

    /**
     * 根据视频id删除阿里云端的视频
     *
     * @param videoSourceId 视频id
     */
    void removeVideoById(String videoSourceId) throws Exception;


    /**
     * 根据视频Id集合删除视频
     *
     * @param videoIdList ID集合
     */
    void removeVideoByIdList(List<String> videoIdList) throws ClientException;

    /**
     * 根据视频Id获取阿里云播放凭证
     *
     * @param videoId 视频Id
     * @return 播放凭证Id
     */
    String getPlayAuth(String videoId) throws ClientException;
}
