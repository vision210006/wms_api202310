package com.smartfactory.apiserver.common.util;


import java.util.UUID;

public class StringUtil {
    public static String generateUUID() {
        UUID uuidObj = UUID.randomUUID();
        return uuidObj.toString();
    }

    public static boolean isEmpty(String value){
        if(value == null || value.isEmpty()){
            return true;
        }
        return false;
    }

    public static String generateRefreshToken(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
