package com.bijianhua.guli.service.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bijianhua.guli.service.base.dto.CourseDto;
import com.bijianhua.guli.service.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bijianhua.guli.service.edu.entity.form.CourseInfoForm;
import com.bijianhua.guli.service.edu.entity.vo.*;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-08
 */
public interface CourseService extends IService<Course> {

    /**
     * 保存课程基本信息
     *
     * @param courseInfo 课程对象
     * @return
     */
    String saveCourseInfo(CourseInfoForm courseInfo);

    /**
     * 根据课程id查询课程信息
     *
     * @param id
     * @return
     */
    CourseInfoForm getCourseFromInfoById(String id);

    /**
     * 根据课程id更新课程信息
     *
     * @param courseInfoForm
     */
    void updateCourseInfoById(CourseInfoForm courseInfoForm);

    /**
     * 课程分页条件查询
     *
     * @param page          当前页
     * @param limit         每页显示条数
     * @param courseQueryVo 条件对象
     * @return
     */
    IPage<CourseVo> selectPage(Long page, Long limit, CourseQueryVo courseQueryVo);


    /**
     * 根据课程id删除封面
     *
     * @param id 课程id
     * @return 是否删除成功
     */
    boolean removeCoverById(String id);

    /**
     * 根据id删除课程
     *
     * @param id 课程id
     * @return
     */
    boolean removeCourseById(String id);

    /**
     * 根据课程id获取课程发布信息
     *
     * @param id 课程id
     * @return
     */
    CoursePublishVo getCoursePublishVoById(String id);

    /**
     * 根据id更新课程状态(发布)
     *
     * @param id 课程id
     * @return
     */
    boolean publishCourseById(String id);

    /**
     * 根据条件对象查询课程
     *
     * @param webCourseQueryVo 条件对象
     * @return 课程集合
     */
    List<Course> webSelectList(WebCourseQueryVo webCourseQueryVo);

    /**
     * 根据课程Id查询课程详细信息
     *
     * @param courserId 课程Id
     * @return
     */
    WebCourseVo selectWebCourseVoById(String courserId);


    /**
     * 获取热门课程(8位)
     *
     * @return
     */
    List<Course> selectHotCourse();

    /**
     * 根据课程id获取课程Dto对象
     * 用于订单信息
     *
     * @param courseId 课程id
     * @return
     */
    CourseDto getCourseDtoById(String courseId);
}
