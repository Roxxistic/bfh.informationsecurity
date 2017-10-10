import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * TASK
 * Please program CBC in Java with JavaStreams.
 */
public class CipherBlockChaining {

    private final int BLOCK_SIZE = 3;

    public static void main(String[] args) {
        CipherBlockChaining cbc = new CipherBlockChaining();
        String plainText;
        char cesarKey;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Message to encode : ");
        plainText = scanner.nextLine();
        System.out.print("Cesar key         : ");
        cesarKey = scanner.nextLine().charAt(0);
        scanner.close();

        System.out.println();
        char[] encrypted = cbc.encrypt(plainText, cesarKey);
        System.out.print("Cipher text       : ");
        cbc.printChars(encrypted);
        System.out.println();
        char[] decrypted = cbc.decrypt(encrypted, cesarKey);
        System.out.print("Decrypted text    : ");
        cbc.printChars(decrypted);
        System.out.println();
    }

    private char[] encrypt(String plainText, char cesarKey)  {
        char[] encrypted = prepareForEncryption(plainText);
        IntStream.range(0,encrypted.length).forEach(i -> {
            if(i >= BLOCK_SIZE) encrypted[i] = (char) (encrypted[i - BLOCK_SIZE] ^ encrypted[i]); // CBC
            encrypted[i] += cesarKey; // apply key
        });
        return encrypted;
    }

    private char[] decrypt(char[] cipherText, char cesarKey){
        char[] decrypted = Arrays.copyOf(cipherText,cipherText.length);
        for(int i = 0; i < decrypted.length; i++){
            decrypted[i] -= cesarKey; // apply key
            if(i >= BLOCK_SIZE){
                decrypted[i] = (char)(decrypted[i] ^ cipherText[i - BLOCK_SIZE]);
            }
        }
        return this.prettify(decrypted);
    }

    private char[] prepareForEncryption(String plaintext){
        char[] prefixed = (this.getInitializationVector() + plaintext).toCharArray();
        char[] prepared = new char[plaintext.length() % BLOCK_SIZE == 0 ? plaintext.length() + BLOCK_SIZE : ((plaintext.length() / BLOCK_SIZE + 2)*BLOCK_SIZE)];
        System.arraycopy(prefixed, 0, prepared,0,prefixed.length);
        return prepared;
    }

    private String getInitializationVector(){
        return "xyz";
    }

    private char[] prettify(char [] decrypted){
        char[] prettified = new char[decrypted.length - BLOCK_SIZE];
        System.arraycopy(decrypted,BLOCK_SIZE,prettified,0,prettified.length);
        return prettified;
    }

    private void printChars(char[] a){
        int blockSize = a.length;
        for(int j = 0; j < blockSize; j++){
            System.out.print(a[j]);
        }
        System.out.println();
    }
}
