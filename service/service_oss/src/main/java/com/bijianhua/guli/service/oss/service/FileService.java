package com.bijianhua.guli.service.oss.service;


import java.io.InputStream;

/**
 * 文件上传至阿里云OSS服务器接口
 */
public interface FileService {

    /**
     * 文件上传至阿里云
     *
     * @param inputStream      输入流
     * @param folder           文件上传至哪个文件夹
     * @param originalFilename 文件原始名称
     * @return 上传的文件在阿里云服务器中的URL
     */
    String fileUpload(InputStream inputStream, String folder, String originalFilename);

    /**
     * 删除文件
     *
     * @param url 文件的url地址
     */
    void removeFile(String url);
}
