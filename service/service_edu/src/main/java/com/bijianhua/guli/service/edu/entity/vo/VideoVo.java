package com.bijianhua.guli.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class VideoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private Boolean free;
    private Integer sort;

    //存储的阿里云地址
    private String videoSourceId;
}