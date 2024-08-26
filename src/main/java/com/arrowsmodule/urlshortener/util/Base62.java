package com.arrowsmodule.urlshortener.util;

import java.math.BigInteger;

public class Base62 {
    private static final char[] ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final int BASE = ALPHABET.length;


    public static String encode(BigInteger number) {
        StringBuilder result = new StringBuilder();

        while (number.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divmod = number.divideAndRemainder(BigInteger.valueOf(BASE));

            result.insert(0, ALPHABET[divmod[1].intValue()]);
            number = divmod[0];
        }

        return result.toString();
    }
}
