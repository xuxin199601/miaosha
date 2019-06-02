package com.xuxin.miaosha.redis;

public class MiaoshaUserKey extends BasePrefix {

    private MiaoshaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;
    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE, "tk");
}
