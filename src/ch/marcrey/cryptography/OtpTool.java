package ch.marcrey.cryptography;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Scanner;

public class OtpTool {

    private Scanner scanner;

    public static void main(String[] args){

        OtpTool tool = new OtpTool();

        tool.scanner = new Scanner(System.in);
        //tool.encryptString();
        //tool.decryptString();
        tool.encryptFile();
        tool.decryptFile();
        tool.scanner.close();
    }

    void encryptString(){
        System.out.println("TEXT ENCRYPTION:");

        System.out.println("Plaintext:         ");
        String text = scanner.nextLine();

        Otp otp = new Otp(stringToBytes(text));
        otp.encrypt();

        System.out.println("Key (B64):         " + bytesToStringBase64(otp.getKey()));
        System.out.println("Ciphertext (B64):  " + bytesToStringBase64(otp.getCiphertext()));
    }

    void decryptString(){
        System.out.println("TEXT DECRYPTION:");

        System.out.println("Ciphertext (B64):    ");
        String ciphertext = scanner.nextLine();

        System.out.println("Key (B64):           ");
        String key = scanner.nextLine();

        Otp otp = new Otp(base64StringToBytes(ciphertext), base64StringToBytes(key));
        otp.decrypt();

        System.out.println("Plaintext:         " + bytesToString(otp.getPlaintext()));
    }

    void encryptFile(){

        System.out.println("FILE ENCRYPTION:");

        try {

            System.out.println("Image:");
            byte[] fileContent = getFileBytes();

            Otp otp = new Otp(fileContent);
            otp.encrypt();

            Path pathCiphertext = Paths.get("enc.jpg");
            Files.write(pathCiphertext, otp.getCiphertext());
            Path pathKey = Paths.get("key.txt");
            Files.write(pathKey, otp.getKey());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void decryptFile(){

        System.out.println("FILE DECRYPTION:");

        try {

            System.out.println("Key file name:       ");
            byte[] keyFileBytes = getFileBytes();

            System.out.println("Image file name:       ");
            byte[] imageFileBytes = getFileBytes();

            Otp otp = new Otp(imageFileBytes, keyFileBytes);
            otp.decrypt();

            Path pathDecrypted = Paths.get("dec.jpg");
            Files.write(pathDecrypted, otp.getPlaintext());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getFileBytes(){

        byte[] fileBytes;

        do{
            try{
                System.out.println("File name:");
                String fileName = scanner.nextLine();

                File fi = new File(fileName);
                fileBytes = Files.readAllBytes(fi.toPath());
                break;
            } catch (Exception e){
                System.out.println("File not found.");
            }
        } while(true);

        return fileBytes;
    }

    private byte[] stringToBytes(String s){
        return s.getBytes();
    }

    private byte[] base64StringToBytes(String s){
        return Base64.getDecoder().decode(s);
    }

    private String bytesToString(byte[] b){
        try {
            return new String(b, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String bytesToStringBase64(byte[] b){
        return Base64.getEncoder().encodeToString(b);
    }
}
