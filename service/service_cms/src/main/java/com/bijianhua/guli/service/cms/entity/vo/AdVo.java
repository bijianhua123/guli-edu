package com.bijianhua.guli.service.cms.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bijianhua
 * @since 2023/3/16
 */
@Data
public class AdVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private Integer sort;
    private String type;
}