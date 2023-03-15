package com.bijianhua.guli.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 查询条件对象
 */
@Data
public class CourseQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String teacherId;
    private String subjectParentId;
    private String subjectId;
}
