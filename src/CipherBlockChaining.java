import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Implements an example of CBC block cypher mode with a very simple encryption key (cesar).
 * @author  Marc Rey
 * @version 10.10.2017
 */
class CipherBlockChaining {

    private int blockSize = 3;

    CipherBlockChaining(int blockSize){
        this.blockSize = blockSize;
    }

    char[] encrypt(String plainText, char cesarKey)  {
        char[] encrypted = prepareForEncryption(plainText);
        IntStream.range(0,encrypted.length).forEach(i -> {
            if(i >= blockSize) encrypted[i] = (char) (encrypted[i - blockSize] ^ encrypted[i]); // CBC
            encrypted[i] += cesarKey; // apply key
        });
        return encrypted;
    }

    char[] decrypt(char[] cipherText, char cesarKey){
        char[] decrypted = Arrays.copyOf(cipherText,cipherText.length);
        IntStream.range(0,decrypted.length).forEach(i -> {
            decrypted[i] -= cesarKey; // apply key
            if(i >= blockSize) decrypted[i] = (char)(decrypted[i] ^ cipherText[i - blockSize]);
        });
        return this.prettify(decrypted);
    }

    private char[] prepareForEncryption(String plaintext){
        char[] prefixed = (this.getInitializationVector() + plaintext).toCharArray();
        char[] prepared = new char[plaintext.length() % blockSize == 0 ? plaintext.length() + blockSize : ((plaintext.length() / blockSize + 2)* blockSize)];
        System.arraycopy(prefixed, 0, prepared,0,prefixed.length);
        return prepared;
    }

    private String getInitializationVector(){
        String result = "";
        Random random = new Random();
        while(result.length() < blockSize){
            result += (char)((random.nextInt(128-32))+32);
        }
        return result;
    }

    private char[] prettify(char [] decrypted){
        char[] prettified = new char[decrypted.length - blockSize];
        System.arraycopy(decrypted, blockSize,prettified,0,prettified.length);
        return prettified;
    }
}
