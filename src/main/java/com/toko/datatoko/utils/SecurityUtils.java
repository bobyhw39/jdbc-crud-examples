package com.toko.datatoko.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class SecurityUtils {
    public static String passwordEncode(String toEncode){
        return BCrypt
                .withDefaults()
                .hashToString(10,toEncode.toCharArray());
    }

    public static boolean verifyPassword(String password,String hashedPassword){
        return BCrypt
                .verifyer()
                .verify(password.toCharArray(), hashedPassword)
                .verified;
    }
}
