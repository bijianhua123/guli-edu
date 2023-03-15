package com.bijianhua.guli.service.edu.service;

import com.bijianhua.guli.service.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-08
 */

public interface VideoService extends IService<Video> {

    void removeMediaVideoById(String id);
}
