package com.bijianhua.guli.service.edu.entity.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * 课程结果对象
 */
@Data
public class CourseVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String subjectParentTitle; //一级标题
    private String subjectTitle; //二级标题
    private String teacherName; //讲师姓名
    private Integer lessonNum;
    private String price;
    private String cover;
    private Long buyCount;
    private Long viewCount;
    private String status;
    private String gmtCreate;
}
