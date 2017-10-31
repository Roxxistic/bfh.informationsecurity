package ch.marcrey.cryptography;

import java.math.BigInteger;

public class RsaTool {

    public static void main(String[] args){

        Rsa rsaEnc = new Rsa();

        /*
        BigInteger m = new BigInteger("24");
        BigInteger p = new BigInteger("7");
        BigInteger q = new BigInteger("11");
        BigInteger e = new BigInteger("37");
        BigInteger d = new BigInteger("13");
        */
        BigInteger m = new BigInteger("123456789");
        BigInteger p = new BigInteger("274177");
        BigInteger q = new BigInteger("616318177");
        BigInteger e = new BigInteger("82421");
        BigInteger d = new BigInteger("32399333229149");

        BigInteger enc = rsaEnc.encrypt(m,e,p,q);

        System.out.println("Encrypted:  " + enc.toString());

        Rsa rsaDec = new Rsa();

        BigInteger dec = rsaDec.decrypt(enc,d,p,q);

        System.out.println("Decrypted:  " + dec.toString());
    }
}
