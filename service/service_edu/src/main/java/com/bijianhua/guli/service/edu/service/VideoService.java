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

    /**
     * 根据课时(视频)ID删除云端视频信息
     *
     * @param videoId 视频ID
     */
    void removeMediaVideoById(String videoId);

    /**
     * 根据章节ID批量删除云端视频信息
     *
     * @param chapterId 章节ID
     */
    void removeMediaVideoByChapterId(String chapterId);

    /**
     * 根据课程ID删除云端视频信息
     *
     * @param courseId 课程ID
     */
    void removeMediaVideoByCouresId(String courseId);

}
