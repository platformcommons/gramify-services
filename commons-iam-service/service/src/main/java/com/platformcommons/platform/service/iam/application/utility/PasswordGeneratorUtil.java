package com.platformcommons.platform.service.iam.application.utility;


import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class PasswordGeneratorUtil {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHAR = "#";
    private static final SecureRandom random = new SecureRandom();

    public String generatePassword() {

        String firstLetter = String.valueOf(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));

        String lowercaseLetters = IntStream.range(0, 3)
                .mapToObj(i -> String.valueOf(LOWERCASE.charAt(random.nextInt(LOWERCASE.length()))))
                .collect(Collectors.joining());

        String digits = IntStream.range(0, 3)
                .mapToObj(i -> String.valueOf(DIGITS.charAt(random.nextInt(DIGITS.length()))))
                .collect(Collectors.joining());

        return firstLetter + lowercaseLetters + digits + SPECIAL_CHAR;
    }
}
