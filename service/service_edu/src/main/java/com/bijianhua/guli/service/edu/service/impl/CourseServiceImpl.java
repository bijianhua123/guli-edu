package com.bijianhua.guli.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.service.edu.entity.*;
import com.bijianhua.guli.service.edu.entity.form.CourseInfoForm;
import com.bijianhua.guli.service.edu.entity.vo.*;
import com.bijianhua.guli.service.edu.feign.OssFileService;
import com.bijianhua.guli.service.edu.mapper.CourseDescriptionMapper;
import com.bijianhua.guli.service.edu.mapper.CourseMapper;
import com.bijianhua.guli.service.edu.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    /**
     * 注入openFeign
     */
    @Autowired
    private OssFileService ossFileService;


    /**
     * 注入课程描述接口
     */
    @Autowired
    private CourseDescriptionMapper courseDescriptionMapper;

    /**
     * 注入课程视频
     */
    @Autowired
    private VideoService videoService;

    /**
     * 注入课程评论
     */
    @Autowired
    private CommentService commentService;

    /**
     * 注入课程章节
     */
    @Autowired
    private ChapterService chapterService;

    /**
     * 注入课程收藏
     */
    @Autowired
    private CourseCollectService courseCollectService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfo) {
        //创建课程对象
        Course course = new Course();
        //拷贝相同字段
        BeanUtils.copyProperties(courseInfo, course);
        //设置默认状态
        course.setStatus(Course.COURSE_DRAFT);
        baseMapper.insert(course);

        //文本档案 纵向拆表 拆分出大文字字段
        CourseDescription courseDescription = new CourseDescription();
        //根据主表id策略
        courseDescription.setId(course.getId());
        courseDescription.setDescription(courseInfo.getDescription());
        courseDescriptionMapper.insert(courseDescription);
        return course.getId();
    }

    @Override
    public CourseInfoForm getCourseFromInfoById(String id) {
        Course course = baseMapper.selectById(id);
        CourseDescription courseDescription = courseDescriptionMapper.selectById(id);
        if (course == null) {
            return null;
        }
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(course, courseInfoForm);
        courseInfoForm.setDescription(courseDescription.getDescription());

        return courseInfoForm;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCourseInfoById(CourseInfoForm courseInfoForm) {
        //创建课程对象
        Course course = new Course();
        //拷贝相同字段
        BeanUtils.copyProperties(courseInfoForm, course);
        baseMapper.updateById(course);

        //文本档案 纵向拆表 拆分出大文字字段
        CourseDescription courseDescription = new CourseDescription();
        //根据主表id策略
        courseDescription.setId(course.getId());
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescriptionMapper.updateById(courseDescription);

    }

    @Override
    public IPage<CourseVo> selectPage(Long page, Long limit, CourseQueryVo courseQueryVo) {
        //条件对象
        QueryWrapper<CourseVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("c.gmt_create");

        String title = courseQueryVo.getTitle();
        String teacherId = courseQueryVo.getTeacherId();
        String subjectId = courseQueryVo.getSubjectId();
        String subjectParentId = courseQueryVo.getSubjectParentId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("c.title", title);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("c.teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("c.subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("c.subject_parent_id", subjectParentId);
        }

        Page<CourseVo> courseVoPage = new Page<>(page, limit);
        List<CourseVo> courseVoList = baseMapper.selecPageByQueryVo(courseVoPage, queryWrapper);

        return courseVoPage.setRecords(courseVoList);
    }

    @Override
    public boolean removeCoverById(String id) {
        //根据id获取课程信息
        Course course = baseMapper.selectById(id);
        if (course != null) {
            //如果课程信息不为null
            String cover = course.getCover();
            //如果url不为空 调用文件服务删除文件
            if (!StringUtils.isEmpty(cover)) {
                R r = ossFileService.removeAvatarById(cover);
                return r.getSuccess();
            }
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeCourseById(String id) {
        //根据courseId删除课程下章节下的视频
        LambdaQueryWrapper<Video> videoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        videoLambdaQueryWrapper.eq(Video::getCourseId, id);
        videoService.remove(videoLambdaQueryWrapper);
        //根据courseId删除章节
        LambdaQueryWrapper<Chapter> chapterLambdaQueryWrapper = new LambdaQueryWrapper<>();
        chapterLambdaQueryWrapper.eq(Chapter::getCourseId, id);
        chapterService.remove(chapterLambdaQueryWrapper);
        //根据courseId删除评论信息
        LambdaQueryWrapper<Comment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commentLambdaQueryWrapper.eq(Comment::getCourseId, id);
        commentService.remove(commentLambdaQueryWrapper);
        //根据courseId删除课程收藏
        LambdaQueryWrapper<CourseCollect> courseCollectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        courseCollectLambdaQueryWrapper.eq(CourseCollect::getCourseId, id);
        courseCollectService.remove(courseCollectLambdaQueryWrapper);
        //根据courseId删除描述信息 本身就是主键
        courseDescriptionMapper.deleteById(id);

        //删除课程本身
        return this.removeById(id);
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("c.id", id);
        return baseMapper.selectCoursePublishVoById(queryWrapper);
    }

    @Override
    public boolean publishCourseById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(Course.COURSE_NORMAL);
        return this.updateById(course);
    }

    @Override
    public List<Course> webSelectList(WebCourseQueryVo webCourseQueryVo) {

        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();

        //查询已发布的课程
        queryWrapper.eq("status", Course.COURSE_NORMAL);

        if (!StringUtils.isEmpty(webCourseQueryVo.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", webCourseQueryVo.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(webCourseQueryVo.getSubjectId())) {
            queryWrapper.eq("subject_id", webCourseQueryVo.getSubjectId());
        }

        if (!StringUtils.isEmpty(webCourseQueryVo.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(webCourseQueryVo.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(webCourseQueryVo.getPriceSort())) {
            if (webCourseQueryVo.getType() == null || webCourseQueryVo.getType() == 1) {
                queryWrapper.orderByDesc("price");
            } else {
                queryWrapper.orderByAsc("price");
            }

        }
        return baseMapper.selectList(queryWrapper);


    }

    @Override
    public WebCourseVo selectWebCourseVoById(String courserId) {
        Course course = baseMapper.selectById(courserId);
        //被访问一次浏览量就增加一次
        Long viewCount = course.getViewCount();
        course.setViewCount(viewCount + 1);
        baseMapper.updateById(course);

        return baseMapper.selectWebCourseVoById(courserId);
    }


}
