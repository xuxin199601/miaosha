package com.xuxin.miaosha.util;

import java.util.UUID;

public class UUIDUtil {

    public static String uuid() {

        //UUID是由一个十六位的数字组成,表现出来的形式例如
        //550E8400-E29B-11D4-A716-446655440000
        return UUID.randomUUID().toString().replace("-", "");
    }
}
