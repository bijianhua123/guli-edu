package com.bijianhua.guli.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bijianhua
 * @since 2023/3/16
 * 网站前端课程查询条件
 */
@Data
public class WebCourseQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String subjectParentId;//二级分类
    private String subjectId; //一级分类
    private String buyCountSort; //销量
    private String gmtCreateSort; //创建时间排序
    private String priceSort; //价格排序
    private Integer type; //价格正倒序

}
