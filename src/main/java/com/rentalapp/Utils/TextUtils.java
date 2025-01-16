package com.rentalapp.Utils;

public class TextUtils {
    public static String getPluralForm(long number, String form1, String form2, String form5) {
        long n = Math.abs(number) % 100;
        long n1 = n % 10;

        if (n > 10 && n < 20) return form5;
        if (n1 > 1 && n1 < 5) return form2;
        if (n1 == 1) return form1;
        return form5;
    }
}