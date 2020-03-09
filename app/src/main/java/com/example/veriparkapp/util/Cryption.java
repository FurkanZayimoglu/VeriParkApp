package com.example.veriparkapp.util;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Cryption {

    public static String encrypt(byte[] cipherText, byte[] aesKey, byte[] aesIV) throws Exception{

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        SecretKeySpec keySpec = new SecretKeySpec(Base64.decode(aesKey, Base64.DEFAULT), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(Base64.decode(aesIV, Base64.DEFAULT));
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] cipherTextEncypt = cipher.doFinal(cipherText);
        return  Base64.encodeToString(cipherTextEncypt, Base64.DEFAULT);
    }

    public static String decrypt(byte[] cipherText, byte[] aesKey, byte[] aesIV) throws Exception
    {

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            SecretKeySpec keySpec = new SecretKeySpec(Base64.decode(aesKey, Base64.DEFAULT), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(Base64.decode(aesIV, Base64.DEFAULT));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decryptedText = cipher.doFinal(Base64.decode(cipherText, Base64.DEFAULT));
            return new String(decryptedText);
    }


}
