package com.bijianhua.guli.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bijianhua.guli.service.edu.entity.Course;
import com.bijianhua.guli.service.edu.entity.Video;
import com.bijianhua.guli.service.edu.feign.VdoFileService;
import com.bijianhua.guli.service.edu.mapper.VideoMapper;
import com.bijianhua.guli.service.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-08
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    /**
     * 注入open接口
     */
    @Autowired
    private VdoFileService vdoFileService;

    @Override
    public void removeMediaVideoById(String id) {
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        vdoFileService.removeMediaVideoById(videoSourceId);
    }

    @Override
    public void removeMediaVideoByChapterId(String chapterId) {
        //先删除章节下的视频信息
        LambdaQueryWrapper<Video> videoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        videoLambdaQueryWrapper.eq(Video::getChapterId, chapterId);
        List<Video> videos = baseMapper.selectList(videoLambdaQueryWrapper);
        List<String> VideoSourceIdList = new ArrayList<>();
        for (Video video : videos) {
            VideoSourceIdList.add(video.getVideoSourceId());
        }
        vdoFileService.removeVideoByIdList(VideoSourceIdList);
    }

    @Override
    public void removeMediaVideoByCouresId(String courseId) {
        LambdaQueryWrapper<Video> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Video::getCourseId, courseId);
        List<Video> videos = baseMapper.selectList(lambdaQueryWrapper);
        List<String> VideoSourceIdList = new ArrayList<>();
        for (Video video : videos) {
            VideoSourceIdList.add(video.getVideoSourceId());
        }
        vdoFileService.removeVideoByIdList(VideoSourceIdList);
    }
}
