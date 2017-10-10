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

        char[] preparedText = cbc.prepareForEncryption(plainText);
        System.out.print("Prepared text     : ");
        cbc.printChars(preparedText);
        System.out.println();
        char[] encrypted = cbc.encrypt(preparedText, cesarKey);
        System.out.print("Cipher text       : ");
        cbc.printChars(encrypted);
        System.out.println();
        char[] decrypted = cbc.decrypt(encrypted, cesarKey);
        System.out.print("Decrypted text    : ");
        cbc.printChars(decrypted);
        System.out.println();
    }

    private char[] encrypt(char[] preparedPlainText, char cesarKey)  {
        IntStream.range(0,preparedPlainText.length).forEach(i -> {
            if(i >= BLOCK_SIZE) preparedPlainText[i] = (char) (preparedPlainText[i - BLOCK_SIZE] ^ preparedPlainText[i]); // CBC
            preparedPlainText[i] += cesarKey; // apply key
        });
        return preparedPlainText;
    }

    private char[] decrypt(char[] cipherText, char cesarKey){
        char[] decrypted = new char[cipherText.length];
        System.arraycopy(cipherText,0,decrypted,0,decrypted.length);
        for(int i = 0; i < decrypted.length; i++){
            decrypted[i] -= cesarKey; // apply key
            if(i >= BLOCK_SIZE){
                decrypted[i] = (char)(decrypted[i] ^ cipherText[i - BLOCK_SIZE]);
            }
        }
        char[] prettified = this.prettify(decrypted);
        return prettified;
    }

    private char[] prepareForEncryption(String plaintext){
        char[] prefixed = (this.getInitializationVector() + plaintext).toCharArray();
        char[] encrypted = new char[plaintext.length() % BLOCK_SIZE == 0 ? plaintext.length() + BLOCK_SIZE : ((plaintext.length() / BLOCK_SIZE + 2)*BLOCK_SIZE)];
        System.arraycopy(prefixed, 0, encrypted,0,prefixed.length);
        return prefixed;
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

    private void printBin(char[] a){
        int blockSize = a.length;
        for(int j = 0; j < blockSize; j++){
            String binary = Integer.toBinaryString(a[j]) + " ";
            for(int k = 0; k < 8 - binary.length(); k++){
                System.out.print("0");
            }
            System.out.print(binary);
        }
    }



}
