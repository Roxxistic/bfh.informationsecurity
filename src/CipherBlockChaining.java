/**
 * TASK
 * Please program CBC in Java with JavaStreams.
 */
public class CipherBlockChaining {

    public static void main(String[] args) {
        CipherBlockChaining cbc = new CipherBlockChaining();

        char[] text = "abcdefghijk".toCharArray();
        char[] iv = "xyz".toCharArray();
        int cesarKey = 5;
        char[] encrypted = cbc.encrypt(text, iv, cesarKey);
        cbc.printChars(encrypted);
        char[] decrypted = cbc.decrypt(encrypted, iv, cesarKey);
        cbc.printChars(decrypted);
    }

    private char[] encrypt(char[] plaintext, char[] iv, int cesarKey)  {
        char[] encrypted = plaintext.length % iv.length == 0 ? new char[plaintext.length + iv.length] : new char[((plaintext.length / iv.length)*iv.length+iv.length) + iv.length];
        System.arraycopy(iv, 0, encrypted, 0, iv.length);
        System.arraycopy(plaintext, 0, encrypted, iv.length, plaintext.length);
        for(int i = 0; i < encrypted.length; i++){
            if(i>=iv.length){
                encrypted[i] = (char) (encrypted[i - iv.length] ^ encrypted[i]);
            }
            // apply key
            //encrypted[i] = (char)(encrypted[i] + (char)cesarKey);
        }
        return encrypted;
    }

    private void printXor(char[] a, char[] b, char[] xor){
        printBin(a);
        System.out.print(" XOR ");
        printBin(b);
        System.out.print(" IS ");
        printBin(xor);
        System.out.println();
    }

    private void printInt(char[][] a){
        int blockCount = a.length;

        // print
        for(int i = 0; i < blockCount; i++){
            printInt(a[i]);
            System.out.print("   ");
        }
        System.out.println();
    }


    private void printChars(char[][] a){
        int blockCount = a.length;

        // print
        for(int i = 0; i < blockCount; i++){
            printChars(a[i]);
            System.out.print("   ");
        }
        System.out.println();
    }

    private void printBin(char[][] a){
        int blockCount = a.length;

        // print
        for(int i = 0; i < blockCount; i++){
            printBin(a[i]);
            System.out.print("   ");
        }
        System.out.println();
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

    private void printInt(char[] a){
        int blockSize = a.length;
        for(int j = 0; j < blockSize; j++){
            System.out.print((int)a[j] + " ");
        }
    }

    private char[] decrypt(char[] cipherText, char[] iv, int cesarKey){
        char[] decrypted = new char[cipherText.length];
        for(int i = 0; i < decrypted.length; i++){
            if(i>=iv.length){
                decrypted[i] = (char) (cipherText[i - iv.length] ^ cipherText[i]);
            } else {
                decrypted[i] = cipherText[i];
            }
            // apply key
            //decrypted[i] = (char)(decrypted[i] - (char)cesarKey);
        }
        return decrypted;
    }



}
