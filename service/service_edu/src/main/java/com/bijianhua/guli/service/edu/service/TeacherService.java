package com.bijianhua.guli.service.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bijianhua.guli.service.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bijianhua.guli.service.edu.entity.vo.TeacherQueryVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-08
 */
public interface TeacherService extends IService<Teacher> {


    /**
     * 条件分页查询
     *
     * @param objectPage   分页对象
     * @param teacherQuery 条件对象
     * @return 分页数据
     */
    IPage<Teacher> selectPage(Page<Teacher> objectPage, TeacherQueryVo teacherQuery);

    /**
     * 根据左侧关键字实现实时查询
     *
     * @param key 关键字
     * @return
     */
    List<Map<String, Object>> selectNameList(String key);

    /**
     * 根据讲师id删除头像
     *
     * @param id 讲师id
     * @return 是否成功
     */
    boolean removeTeacherAvatarById(String id);
}
