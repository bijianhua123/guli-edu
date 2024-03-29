package com.bijianhua.guli.service.edu.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bijianhua.guli.service.base.dto.CourseDto;
import com.bijianhua.guli.service.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bijianhua.guli.service.edu.entity.vo.CoursePublishVo;
import com.bijianhua.guli.service.edu.entity.vo.CourseVo;
import com.bijianhua.guli.service.edu.entity.vo.WebCourseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-08
 */
public interface CourseMapper extends BaseMapper<Course> {


    List<CourseVo> selecPageByQueryVo(Page<CourseVo> courseVoPage,
                                      @Param(Constants.WRAPPER) QueryWrapper<CourseVo> queryWrapper);

    /**
     * 根据id查询课程相关信息
     *
     * @param queryWrapper
     * @return
     */
    CoursePublishVo selectCoursePublishVoById(@Param(Constants.WRAPPER) QueryWrapper<Course> queryWrapper);

    /**
     * 根据课程Id查询课程详细信息
     *
     * @param courserId 课程Id
     * @return
     */
    WebCourseVo selectWebCourseVoById(String courserId);

    /**
     * 根据课程id获取课程Dto对象
     * 用于订单信息
     *
     * @param courseId 课程id
     * @return
     */
    CourseDto selectCourseDtoById(String courseId);
}
