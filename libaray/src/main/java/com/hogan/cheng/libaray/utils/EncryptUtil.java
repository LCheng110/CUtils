package com.hogan.cheng.libaray.utils;

import android.util.Base64;

import java.security.MessageDigest;

/**
 * Created by liucheng on 17/10/26.
 * 算法工具
 */
public class EncryptUtil {

    // 加密
    public static String getBase64(String str) {

        return Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
    }

    /*生成MD5值*/
    public static String md5(String code) {

        String s = null;

        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            byte[] code_byts = code.getBytes("UTF-8");

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(code_byts);
            byte tmp[] = md.digest();          // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char str[] = new char[16 * 2];   // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0;                                // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) {          // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte byte0 = tmp[i];                 // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf];            // 取字节中低 4 位的数字转换
            }
            s = new String(str);                                 // 换后的结果转换为字符串

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 用SHA1算法生成安全签名
     *
     * @param str 待加密字符串
     * @return 安全签名
     */
    public static String sha1(String str) {
        try {
//            String[] array = new String[] { token, timestamp, nonce};
//            StringBuffer sb = new StringBuffer();
//            // 字符串排序
//            Arrays.sort(array);
//            for (int i = 0; i < 3; i++) {
//                sb.append(array[i]);
//            }
//            String str = sb.toString();
            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
