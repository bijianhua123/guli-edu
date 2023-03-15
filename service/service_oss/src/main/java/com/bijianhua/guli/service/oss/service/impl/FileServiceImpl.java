package com.bijianhua.guli.service.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.bijianhua.guli.service.oss.service.FileService;
import com.bijianhua.guli.service.oss.util.OssProperties;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {

    //注入阿里云属性
    @Autowired
    private OssProperties ossProperties;


    @Override
    public String fileUpload(InputStream inputStream, String folder, String originalFilename) {
        //读取阿里云配置信息
        String endpoint = ossProperties.getEndpoint();
        String keyid = ossProperties.getKeyid();
        String keysecret = ossProperties.getKeysecret();
        String bucketname = ossProperties.getBucketname();


        //创建ossclint实例核心对象
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyid, keysecret);
        //判断bucket是否存在
        boolean isExist = ossClient.doesBucketExist(bucketname);
        //如果不存在 创建bucket
        if (!isExist) {
            //通过名称创建
            ossClient.createBucket(bucketname);
            //设置访问权限
            ossClient.setBucketAcl(bucketname, CannedAccessControlList.PublicRead);
        }

        //构建objectName 类似这种 avatar/2023/3/9/v.webp 文件分层策略 按日期进行分类
        //获取当前日期作为文件目录
        String dateFolderName = new DateTime().toString("yyyy/MM/dd");
        //使用UUID生成文件名
        String fileName = UUID.randomUUID().toString();
        //截取文件拓展名
        String fileExpand = originalFilename.substring(originalFilename.lastIndexOf("."));
        //拼成完整文件名
        String key = folder + "/" + dateFolderName + "/" + fileName + fileExpand;
        /*
        上传文件流参数
        1.bucketname
        2.文件名
        3.文件输入流
        */
        ossClient.putObject(bucketname, key, inputStream);
        //关闭流
        ossClient.shutdown();
        //https://guli-file-bjh.oss-cn-beijing.aliyuncs.com/avatar/v.webp
        //返回文件地址
        return "https://" + bucketname + "." + endpoint + "/" + key;
    }

    @Override
    public void removeFile(String url) {
        //读取阿里云配置信息
        String endpoint = ossProperties.getEndpoint();
        String keyid = ossProperties.getKeyid();
        String keysecret = ossProperties.getKeysecret();
        String bucketname = ossProperties.getBucketname();

        //创建ossclint实例核心对象
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyid, keysecret);
        //https://guli-file-bjh.oss-cn-beijing.aliyuncs.com/avatar/2023/03/09/0cd03e99-3e7f-44d0-bfa3-f79376fdce3e.jpg
        String host = "https://" + bucketname + "." + endpoint + "/";
        String objectName = url.substring(host.length());
        // 删除文件或目录。如果要删除目录，目录必须为空。
        ossClient.deleteObject(bucketname, objectName);
    }
}
