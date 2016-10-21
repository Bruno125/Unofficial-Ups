package com.brunoaybar.unofficialupc.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility class that executes encryption operations
 */

public class CryptoUtils {

    public static String encryptPassword(String password){
        byte[] encrypted = new byte[0];
        try {
            encrypted = encryptAES(password.getBytes("UTF-8"), "0000000000000000".getBytes("UTF-8"),
                    "0000000000000000".getBytes("UTF-8"));
        } catch (Exception e) {
            return "";
        }
        return Base64.encodeToString(encrypted,Base64.DEFAULT).replace("\n","");
    }


    private static byte[] encryptAES(byte[] bytes, byte[] key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
        return cipher.doFinal(bytes);
    }
}
