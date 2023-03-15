package com.bijianhua.guli.service.edu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.bijianhua.guli.service.base.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课程简介
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("edu_course_description")
@ApiModel(value = "CourseDescription对象", description = "课程简介")
public class CourseDescription extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /*覆盖主键全局id的策略*/
    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.NONE)
    private String id;

    @ApiModelProperty(value = "课程简介")
    private String description;


}
