package com.bijianhua.guli.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 用户展示课程树形嵌套列表
 */
@Data
public class SubjectVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private Integer sort;
    private List<SubjectVo> children = new ArrayList<>();
}
