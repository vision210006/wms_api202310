package com.smartfactory.apiserver.common.constant;

import java.util.Map;

public class Constant {

    //for Encrypt
    public static final String ENCRYPT_ALG = "AES/CBC/PKCS5Padding";
    public static final String ENCRYPT_KEY = "01234567899254321111012345678901";
    public static final String ENCRYPT_IV = ENCRYPT_KEY.substring(0, 16); // 16byte

    public static final String CLIENT_ID = "stockmore119";
    public static final String CLIENT_SECRET = "tmxhrahdj123!@#stock=1231=11zzzz";
}
