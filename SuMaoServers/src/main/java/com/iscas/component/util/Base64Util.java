package com.iscas.component.util;

import sun.misc.BASE64Decoder;

import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 * User: adams
 * Date:  2017/11/30
 * Time: 11:45
 */
public class Base64Util {
    /**
     * 将 s 进行 BASE64 编码
     *
     * @return String
     * @author lifq
     * @date 2015-3-4 上午09:24:02
     */
    public static String encode(String s) {
        if (s == null)
            return null;
        String res = "";
        try {
            res = new sun.misc.BASE64Encoder().encode(s.getBytes("GBK"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 将 BASE64 编码的字符串 s 进行解码
     *
     * @return String
     * @author lifq
     * @date 2015-3-4 上午09:24:26
     */
    public static String decode(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b, "GBK");
        } catch (Exception e) {
            return null;
        }
    }
}

