package com.mascara.oyo_booking_backend.utils;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/11/2023
 * Time      : 6:03 CH
 * Filename  : GenerateCodeUtils
 */
public class GenerateCodeUtils {

    public static String generateCode(String alias, Integer count) {
        int newCount = count + 1;
        String formattedCount = String.format("%03d", newCount);
        return alias + "_" + formattedCount;
    }
}
