package com.bijianhua.guli.service.base.exception;


import com.bijianhua.guli.common.base.result.ResultCodeEnum;
import lombok.Data;

/**
 * 自定义异常
 */
@Data
public class GuliException extends RuntimeException {

    //错误码
    private Integer code;

    public GuliException(String message, Integer code) {
        super(message); //来自父类的错误信息
        this.code = code;
    }

    //使用枚举类
    public GuliException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "GuliException{" +
                "code=" + code +
                "message=" + this.getMessage() +
                '}';
    }
}
