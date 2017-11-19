package ch.marcrey.cryptography;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BirthdayParadox {
    public static void main(String[] args){
        BigDecimal oppositeP = new BigDecimal("1");

        int n = 254;

        for(int i = 1; i <= n; i++){
            BigDecimal p = new BigDecimal("365");
            p = p.subtract(new BigDecimal(i));
            p = p.divide(new BigDecimal("365"), 20, RoundingMode.HALF_UP);
            oppositeP = oppositeP.multiply(p);
            BigDecimal r = new BigDecimal("1").subtract(oppositeP);
            System.out.println("Result: " + r.toString());
        }
    }
}
