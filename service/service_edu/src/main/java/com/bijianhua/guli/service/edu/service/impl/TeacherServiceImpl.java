package com.bijianhua.guli.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.service.edu.entity.Course;
import com.bijianhua.guli.service.edu.entity.Teacher;
import com.bijianhua.guli.service.edu.entity.vo.TeacherQueryVo;
import com.bijianhua.guli.service.edu.feign.OssFileService;
import com.bijianhua.guli.service.edu.mapper.TeacherMapper;
import com.bijianhua.guli.service.edu.service.CourseService;
import com.bijianhua.guli.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-08
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    //注入openFeign远程调用
    @Autowired
    private OssFileService ossFileService;

    @Autowired
    private CourseService courseService;

    @Override
    public IPage<Teacher> selectPage(Page<Teacher> objectPage, TeacherQueryVo teacherQuery) {
        //1.创建条件对象
        LambdaQueryWrapper<Teacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //2.根据sort字段进行排序
        lambdaQueryWrapper.orderByAsc(Teacher::getSort);
        //3.判断条件查询对象是否为空
        if (teacherQuery == null) {
            return baseMapper.selectPage(objectPage, lambdaQueryWrapper);
        }
        //4.获取条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String joinDateBegin = teacherQuery.getJoinDateBegin();
        String joinDateEnd = teacherQuery.getJoinDateEnd();
        //4.1 判断姓名是否为空 加入模糊查询列表
        if (!StringUtils.isEmpty(name)) {
            lambdaQueryWrapper.like(Teacher::getName, name);
        }
        //4.2 判断等级条件是否为空 加入查询列表
        if (!StringUtils.isEmpty(level)) {
            lambdaQueryWrapper.eq(Teacher::getLevel, level);
        }
        //4.3 判断开始时间是否为空
        if (!StringUtils.isEmpty(joinDateBegin)) {
            lambdaQueryWrapper.ge(Teacher::getJoinDate, joinDateBegin);
        }
        //4.4 判断结束时间是否为空
        if (!StringUtils.isEmpty(joinDateEnd)) {
            lambdaQueryWrapper.le(Teacher::getJoinDate, joinDateEnd);
        }
        //5.返回结果
        return baseMapper.selectPage(objectPage, lambdaQueryWrapper);
    }

    @Override
    public List<Map<String, Object>> selectNameList(String key) {
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        //仅查询列名name
        queryWrapper.select(Teacher::getName);
        //设置查询条件
        queryWrapper.likeRight(Teacher::getName, "周");

        List<Map<String, Object>> mapList = baseMapper.selectMaps(queryWrapper);
        return mapList;
    }

    @Override
    public boolean removeTeacherAvatarById(String id) {
        Teacher teacher = baseMapper.selectById(id);
        if (teacher != null) {
            String avatar = teacher.getAvatar();
            if (!StringUtils.isEmpty(avatar)) {
                R r = ossFileService.removeAvatarById(avatar);
                return r.getSuccess();
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> getTeacherAndCourseById(String teacherId) {
        //根据id获取讲师
        Teacher teacher = baseMapper.selectById(teacherId);
        LambdaQueryWrapper<Course> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Course::getTeacherId, teacherId);
        List<Course> courseList = courseService.list(lambdaQueryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("teacher", teacher);
        map.put("courseList", courseList);
        return map;
    }

    @Override
    public List<Teacher> selectHotTeacher() {
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Teacher::getSort);
        queryWrapper.last("limit 4");
        return baseMapper.selectList(queryWrapper);
    }
}
