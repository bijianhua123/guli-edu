package com.bijianhua.guli.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bijianhua.guli.service.edu.entity.Chapter;
import com.bijianhua.guli.service.edu.entity.Course;
import com.bijianhua.guli.service.edu.entity.Video;
import com.bijianhua.guli.service.edu.entity.vo.ChapterVo;
import com.bijianhua.guli.service.edu.entity.vo.VideoVo;
import com.bijianhua.guli.service.edu.feign.VdoFileService;
import com.bijianhua.guli.service.edu.mapper.ChapterMapper;
import com.bijianhua.guli.service.edu.mapper.VideoMapper;
import com.bijianhua.guli.service.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bijianhua.guli.service.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-08
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    /**
     * 注入课程视频
     */
    @Autowired
    private VideoMapper videoMapper;

    /**
     * 注入open接口
     */
    @Autowired
    private VdoFileService vdoFileService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeChapterById(String id) {
        //先删除章节下的视频信息
        LambdaQueryWrapper<Video> videoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        videoLambdaQueryWrapper.eq(Video::getChapterId, id);
        videoMapper.delete(videoLambdaQueryWrapper);

        //删除章节
        return this.removeById(id);
    }

    @Override
    public List<ChapterVo> nestedList(String id) {
        //根据课程id获取章节信息
        QueryWrapper<Chapter> chapterLambdaQueryWrapper = new QueryWrapper<>();
        chapterLambdaQueryWrapper.eq("course_id", id);
        chapterLambdaQueryWrapper.orderByAsc("sort", "id");
        List<Chapter> chapters = baseMapper.selectList(chapterLambdaQueryWrapper);


        //根据课程id先获取所有视频信息
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        videoQueryWrapper.orderByAsc("sort", "id");
        List<Video> videos = videoMapper.selectList(videoQueryWrapper);

        //组装数据
        List<ChapterVo> chapterVos = new ArrayList<>();

        for (Chapter chapter : chapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVos.add(chapterVo);
            List<VideoVo> videoVoList = new ArrayList<>();
            for (Video video : videos) {
                if (chapter.getId().equals(video.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
        }
        return chapterVos;
    }

}
