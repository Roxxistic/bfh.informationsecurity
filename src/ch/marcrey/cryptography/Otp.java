package ch.marcrey.cryptography;

import java.security.SecureRandom;

class Otp {

    private byte[] plaintext;
    private byte[] ciphertext;
    private byte[] key;

    Otp(byte[] plaintext){
        this.plaintext = plaintext;
    }

    Otp(byte[] ciphertext, byte[] key){
        this.ciphertext = ciphertext;
        this.key = key;
    }

    byte[] getCiphertext(){
        return ciphertext;
    }

    byte[] getKey(){
        return key;
    }

    byte[] getPlaintext(){
        return plaintext;
    }

    void encrypt(){
        // generate key
        key = new byte[plaintext.length];
        new SecureRandom().nextBytes(key);

        // encrypt
        ciphertext = xor(plaintext, key);
    }

    void decrypt(){
        plaintext = xor(ciphertext, key);
    }

    private byte[] xor(byte[] a, byte[] b){
        if(a.length != b.length){
            return null;
        }
        byte[] xor = new byte[a.length];
        for(int i = 0; i < a.length; i++ ){
            xor[i] = (byte)(a[i] ^ b[i]);
        }
        return xor;
    }
}

