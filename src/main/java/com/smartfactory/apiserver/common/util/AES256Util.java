package com.smartfactory.apiserver.common.util;



import com.smartfactory.apiserver.common.constant.Constant;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES256Util {
    public static String encrypt(String text) throws Exception {
        Cipher cipher = Cipher.getInstance(Constant.ENCRYPT_ALG);
        SecretKeySpec keySpec = new SecretKeySpec(Constant.ENCRYPT_IV.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(Constant.ENCRYPT_IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(Constant.ENCRYPT_ALG);
        SecretKeySpec keySpec = new SecretKeySpec(Constant.ENCRYPT_IV.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(Constant.ENCRYPT_IV.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, "UTF-8");
    }
}
