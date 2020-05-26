package com.echo.moviememoir.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Helper {
    public static String getMD5(String str) throws Exception {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());

            String md5Str = new BigInteger(1, md.digest()).toString(16);
            if (md5Str.length() < 32) {
                md5Str = 0 + md5Str;
            }
            return md5Str;
        } catch (Exception e) {
            throw new Exception("MD5加密出现错误");
        }
    }
}
