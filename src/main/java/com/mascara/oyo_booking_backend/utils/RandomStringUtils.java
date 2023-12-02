package com.mascara.oyo_booking_backend.utils;

import java.util.Random;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 03/12/2023
 * Time      : 3:09 SA
 * Filename  : RandomStringUtils
 */
public class RandomStringUtils {
    public static final int NUM_CHARACTER_REQUIRED = 8;

    public static String generateRandomString() {
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialCharacters = "!@#$%^&*()";

        String allCharacters = uppercaseLetters + lowercaseLetters + numbers + specialCharacters;
        Random random = new Random();
        StringBuilder randomString = new StringBuilder(NUM_CHARACTER_REQUIRED);
        char uppercaseChar = uppercaseLetters.charAt(random.nextInt(uppercaseLetters.length()));
        randomString.append(uppercaseChar);

        char lowercaseChar = lowercaseLetters.charAt(random.nextInt(lowercaseLetters.length()));
        randomString.append(lowercaseChar);

        char numberChar = numbers.charAt(random.nextInt(numbers.length()));
        randomString.append(numberChar);

        char specialChar = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        randomString.append(specialChar);

        for (int i = 0; i < NUM_CHARACTER_REQUIRED - 4; i++) {
            char randomChar = allCharacters.charAt(random.nextInt(allCharacters.length()));
            randomString.append(randomChar);
        }

        char[] chars = randomString.toString().toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }
}
