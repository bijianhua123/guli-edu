package com.bijianhua.guli.service.edu.service.impl;

import com.bijianhua.guli.service.edu.entity.Video;
import com.bijianhua.guli.service.edu.feign.VdoFileService;
import com.bijianhua.guli.service.edu.mapper.VideoMapper;
import com.bijianhua.guli.service.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
