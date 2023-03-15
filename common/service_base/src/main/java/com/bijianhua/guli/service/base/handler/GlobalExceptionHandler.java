package com.bijianhua.guli.service.base.handler;


import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.common.base.util.ExceptionUtils;
import com.bijianhua.guli.service.base.exception.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.bijianhua.guli.common.base.result.ResultCodeEnum.BAD_SQL_GRAMMAR;
import static com.bijianhua.guli.common.base.result.ResultCodeEnum.JSON_PARSE_ERROR;

/**
 * 全局异常类
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常处理
     *
     * @param e 所有异常
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public R exception(Exception e) {
        log.error(ExceptionUtils.getMessage(e));
        //e.printStackTrace();
        return R.error();
    }

    /**
     * 特定异常处理(默认先匹配最接近的异常)
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BadSqlGrammarException.class)
    public R error(BadSqlGrammarException e) {
        log.error(ExceptionUtils.getMessage(e));
        //e.printStackTrace();
        return R.setResult(BAD_SQL_GRAMMAR);
    }

    /**
     * json异常处理
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R error(HttpMessageNotReadableException e) {
        log.error(ExceptionUtils.getMessage(e));
        //e.printStackTrace();
        return R.setResult(JSON_PARSE_ERROR);
    }


    @ResponseBody
    @ExceptionHandler(GuliException.class)
    public R GuliException(GuliException e) {
        log.error(ExceptionUtils.getMessage(e));
        return R.error().message(e.getMessage()).code(e.getCode());
    }

}
