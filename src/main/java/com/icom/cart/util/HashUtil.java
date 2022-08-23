package com.icom.cart.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashUtil {
    public static String sha256ToBase64(Object o) {
        try {
            String input = JsonUtil.objectToString(o);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                    input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String input = "abc1234NvWD3Rb04eIB6x5vbY41osyzu+JljeRrT/rnsOnthy4=";
        System.out.println("SHA256 of " + input + " is: " + sha256ToBase64(input));
    }
}
