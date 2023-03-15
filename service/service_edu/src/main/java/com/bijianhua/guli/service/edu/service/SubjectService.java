package com.bijianhua.guli.service.edu.service;

import com.bijianhua.guli.service.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bijianhua.guli.service.edu.entity.vo.SubjectVo;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-08
 */
public interface SubjectService extends IService<Subject> {

    /**
     * 通过文件流批量读取数据
     *
     * @param inputStream
     */
    void batchImport(InputStream inputStream);

    /**
     * 获取课程树形嵌套列表
     *
     * @return
     */
    List<SubjectVo> nestedList();
}
