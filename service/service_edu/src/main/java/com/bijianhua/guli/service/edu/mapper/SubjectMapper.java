package com.bijianhua.guli.service.edu.mapper;

import com.bijianhua.guli.service.edu.entity.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bijianhua.guli.service.edu.entity.vo.SubjectVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-08
 */
public interface SubjectMapper extends BaseMapper<Subject> {


    /**
     * 查询课程嵌套列表
     *
     * @param parentId
     * @return
     */
    List<SubjectVo> selectNestedListByParentId(@Param("parentId") String parentId);
}
