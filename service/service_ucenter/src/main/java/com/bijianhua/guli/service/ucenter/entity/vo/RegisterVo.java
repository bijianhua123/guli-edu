package com.bijianhua.guli.service.ucenter.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bijianhua
 * @since 2023/3/18
 */
@Data
public class RegisterVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nickname;
    private String mobile;
    private String password;
    private String code;
}
