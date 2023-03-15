package com.bijianhua.guli.service.vod.service;


import com.aliyuncs.exceptions.ClientException;

import java.io.InputStream;

/**
 * 视频上传接口
 */
public interface VideoService {

    /**
     * 上传视频到艾丽云
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


}