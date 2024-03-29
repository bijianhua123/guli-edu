package com.bijianhua.guli.common.base.util;

/**
 * @author bijianhua
 * @since 2023/3/18
 */
public class RedisConstants {

    public static final String LOGIN_CODE_KEY = "login:code:";
    public static final Long LOGIN_CODE_TTL = 5L;
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 1800L;
}
