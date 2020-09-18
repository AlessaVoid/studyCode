package com.boco.TONY.utils;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

/**
 * @author tony
 * @describe PasswordEncryptHelper
 * @date 2019-09-11
 */
public class PasswordEncryptHelper {
    private static final String PUBLIC_SALT = "web";
    private static final int iteratorTimes = 3;

    public static String encrypt(String operSalt, String password) {
        // Ĭ��ʹ�õ���SHA-512�㷨
        DefaultHashService service = new DefaultHashService();
        service.setHashAlgorithmName("SH5-512");// ʹ��SHA512�㷨
        service.setPrivateSalt(new SimpleByteSource(operSalt));// ����˽��
        service.setGeneratePublicSalt(true);
        service.setRandomNumberGenerator(new SecureRandomNumberGenerator());
        service.setHashIterations(iteratorTimes);
        HashRequest request = new HashRequest.Builder()
                .setAlgorithmName("MD5")
                .setSource(ByteSource.Util.bytes(password))
                .setSalt(ByteSource.Util.bytes(PUBLIC_SALT))
                .setIterations(iteratorTimes)
                .build();
        return service.computeHash(request).toHex();
    }




    public static void main(String[] args) {
        System.out.println(    encrypt("20190719210","000000"));

    }
}
