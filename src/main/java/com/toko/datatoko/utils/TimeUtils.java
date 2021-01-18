package com.toko.datatoko.utils;

import java.sql.Date;

public class TimeUtils {
    public static Date currentDateTime(){
        return new java.sql.Date(System.currentTimeMillis());
    }
}
