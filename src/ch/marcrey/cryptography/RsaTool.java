package ch.marcrey.cryptography;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class RsaTool {

    BigInteger p = new BigInteger("274177");
    BigInteger q = new BigInteger("616318177");
    BigInteger e = new BigInteger("82421");
    BigInteger d = new BigInteger("32399333229149");

    public static void main(String[] args){

        RsaTool tool = new RsaTool();
        tool.exec();
    }

    private void exec(){

        Rsa rsa = new Rsa();

        System.out.println("INTEGER ENCRYPTION");
        String messageA = "123456789";
        System.out.println("Plain Text: " + messageA);
        BigInteger encA = rsa.encrypt(new BigInteger(messageA),e,p,q);
        System.out.println("Encrypted:  " + encA);
        BigInteger decA = rsa.encrypt(encA,d,p,q);
        System.out.println("Decrypted:  " + decA);

        System.out.println("STRING ENCRYPTION");
        String messageB = "Secret"; // may not be to long!!
        System.out.println("Plain Text: " + messageB);
        BigInteger encB = rsa.encrypt(new BigInteger(stringToBytes(messageB)),e,p,q);
        System.out.println("Encrypted:  " + encB.toString());
        BigInteger decB = rsa.encrypt(encB,d,p,q);
        System.out.println("Decrypted:  " + bytesToString(decB.toByteArray()));
    }

    private byte[] stringToBytes(String s){
        return s.getBytes();
    }

    private String bytesToString(byte[] b){
        try {
            return new String(b, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
