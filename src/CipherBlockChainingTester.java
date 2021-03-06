import java.util.Scanner;

/**
 * Use to try out the cbc-cesar-encryption.
 * @author  Marc Rey
 * @version 10.10.2017
 */
public class CipherBlockChainingTester {

    public static void main(String[] args) {
        String plainText;
        char cesarKey;
        int blockSize = 3;
        CipherBlockChaining cbc = new CipherBlockChaining(blockSize);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Message to encode : ");
        plainText = scanner.nextLine();
        System.out.print("Cesar key (1 char): ");
        cesarKey = scanner.nextLine().charAt(0);
        scanner.close();
        char[] encrypted = cbc.encrypt(plainText, cesarKey);
        System.out.print("Cipher text       : ");
        CipherBlockChainingTester.printChars(encrypted);
        System.out.println();
        char[] decrypted = cbc.decrypt(encrypted, cesarKey);
        System.out.print("Decrypted text    : ");
        CipherBlockChainingTester.printChars(decrypted);
        System.out.println();
    }

    private static void printChars(char[] a){
        for (char c : a) {
            System.out.print(c);
        }
    }
}
