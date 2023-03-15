package com.bijianhua.guli.service.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bijianhua.guli.service.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bijianhua.guli.service.edu.entity.form.CourseInfoForm;
import com.bijianhua.guli.service.edu.entity.vo.CoursePublishVo;
import com.bijianhua.guli.service.edu.entity.vo.CourseQueryVo;
import com.bijianhua.guli.service.edu.entity.vo.CourseVo;

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
}
