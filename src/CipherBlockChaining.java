/**
 * TASK
 * Please program CBC in Java with JavaStreams.
 */
public class CipherBlockChaining {

    private final int BLOCK_LENGTH = 3;

    public static void main(String[] args) {
        CipherBlockChaining cbc = new CipherBlockChaining();
        char[] text = "abcdefghijk".toCharArray();
        char cesarKey = ' ';
        char[] encrypted = cbc.encrypt(text, cesarKey);
        cbc.printChars(encrypted);
        char[] decrypted = cbc.decrypt(encrypted, cesarKey);
        cbc.printChars(decrypted);
    }

    private char[] encrypt(char[] plaintext, char cesarKey)  {
        char[] iv = this.getInitializationVector();
        char[] encrypted = plaintext.length % iv.length == 0 ? new char[plaintext.length + iv.length] : new char[((plaintext.length / iv.length)*iv.length+iv.length) + iv.length];
        System.arraycopy(iv, 0, encrypted, 0, iv.length);
        System.arraycopy(plaintext, 0, encrypted, iv.length, plaintext.length);
        for(int i = 0; i < encrypted.length; i++){
            if(i>=iv.length){
                encrypted[i] = (char) (encrypted[i - iv.length] ^ encrypted[i]);
            }
            // apply key
        }
        return encrypted;
    }

    private char[] decrypt(char[] cipherText, char cesarKey){
        char[] iv = this.getInitializationVector();
        char[] decrypted = new char[cipherText.length];
        for(int i = 0; i < decrypted.length; i++){
            // apply key
            if(i>=iv.length){
                decrypted[i] = (char) (cipherText[i - iv.length] ^ cipherText[i]);
            } else {
                decrypted[i] = cipherText[i];
            }

        }
        return decrypted;
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

    private char[] getInitializationVector(){
        return "xyz".toCharArray();
    }

}
