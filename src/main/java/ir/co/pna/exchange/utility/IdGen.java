package ir.co.pna.exchange.utility;

import java.math.BigInteger;

public class IdGen {

    public static String generateId(String str) {
        if (str.length() != 13) {
            return "bad input";
        }
        int[] coefficients = {13, 12, 11, 10, 9, 1, 2, 3, 4, 5, 6, 7, 8};
        int tmp = 0;
        for (int i = 0; i < str.length(); i++) {
            int j = Character.getNumericValue(str.charAt(i));
            tmp += coefficients[i] * j;
        }
        int r = tmp % 99;
        return str + "/" + r;
    }
}
