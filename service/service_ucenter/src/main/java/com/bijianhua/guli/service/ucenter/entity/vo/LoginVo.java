package com.bijianhua.guli.service.ucenter.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bijianhua
 * @since 2023/3/19
 */
@Data
public class LoginVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String mobile;
    private String password;
}
