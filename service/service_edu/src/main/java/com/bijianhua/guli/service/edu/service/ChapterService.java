package com.bijianhua.guli.service.edu.service;

import com.bijianhua.guli.service.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bijianhua.guli.service.edu.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-08
 */
public interface ChapterService extends IService<Chapter> {

    /**
     * 根据id删除章节信息
     *
     * @param id
     * @return
     */
    boolean removeChapterById(String id);

    /**
     * 根据课程id获取章节嵌套列表
     * @param id
     * @return
     */
    List<ChapterVo> nestedList(String id);
}
