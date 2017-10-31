import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class OtpEncryption {

    public static void main(String[] args){

        System.out.print("Enter text to encrypt: ");
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        scanner.close();

        OtpEncryption otp = new OtpEncryption();

        KeyAndCiphertext keyAndCiphertext = otp.encrypt(text);

        System.out.println("Encryption:");
        System.out.println("Plaintext (B64):   " + text);
        System.out.println("Key (B64):         " + keyAndCiphertext.getKey());
        System.out.println("Ciphertext (B64):  " + keyAndCiphertext.getCiphertext());

        String decrypted = otp.decrypt(keyAndCiphertext);
        System.out.println("Dencryption:");
        System.out.println("Decrypted text:    " + decrypted);
    }

    public KeyAndCiphertext encrypt(String text){
        try {
            // prepare plaintext
            byte[] plaintext = text.getBytes("UTF-8");

            // generate key
            byte[] key = new byte[plaintext.length];
            new SecureRandom().nextBytes(key);

            // generate ciphertext
            byte[] ciphertext = xor(plaintext, key);

            String key64 = Base64.getEncoder().encodeToString(key);
            String ciphertext64 = Base64.getEncoder().encodeToString(ciphertext);

            return new KeyAndCiphertext(key64, ciphertext64);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decrypt(KeyAndCiphertext keyAndCiphertext){
        try {
            byte[] key = Base64.getDecoder().decode(keyAndCiphertext.getKey());
            byte[] ciphertext = Base64.getDecoder().decode(keyAndCiphertext.getCiphertext());
            byte[] plaintext = xor(ciphertext, key);
            return new String(plaintext, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

