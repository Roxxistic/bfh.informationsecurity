import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * Implements an example of CBC block cypher mode with AES encryption.
 * @author  Marc Rey
 * @version 15.10.2017
 */
class CbcAes {

    private int cbcBlockSize;
    private SecretKeySpec secretKey;
    private byte[] key;

    CbcAes(int cbcBlockSizeFactor){
        this.cbcBlockSize = cbcBlockSizeFactor * 16;
    }

    public static void main(String[] args)
    {
        final String secretKey = "topsecretkey";
        String originalString =  "012345678901234567890123456788901237890123456789012345678901234567890123456789";
        CbcAes cbcAes = new CbcAes(1);

        String encryptedString = cbcAes.encrypt(originalString, secretKey) ;
        String decryptedString = cbcAes.decrypt(encryptedString, secretKey) ;

        System.out.println(originalString);
        System.out.println(encryptedString);
        System.out.println(decryptedString);
    }

    String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] plaintextAsBytes = strToEncrypt.getBytes("UTF-8");

            int ivLength = cbcBlockSize;
            int plaintextLength = plaintextAsBytes.length;
            int paddingLength = plaintextLength % cbcBlockSize == 0 ? cbcBlockSize : (cbcBlockSize - plaintextLength % cbcBlockSize);
            int cypherLength = ivLength + plaintextLength + paddingLength;

            byte[] cyphertextAsBytes = new byte[cypherLength];

            byte[] iv = new byte[cbcBlockSize];
            SecureRandom random = new SecureRandom();
            for(int i = 0; i < cbcBlockSize; i++){
                iv[i] = (byte)((random.nextInt(128-32))+32);
            }

            System.arraycopy(iv, 0, cyphertextAsBytes, 0, cbcBlockSize);

            System.arraycopy(plaintextAsBytes, 0, cyphertextAsBytes, cbcBlockSize, plaintextAsBytes.length);

            // block-wise XOR and encryption
            for(int i = 0; i < cypherLength; i += cbcBlockSize){
                byte[] plaintextBlock = new byte[cbcBlockSize];
                System.arraycopy(cyphertextAsBytes, i, plaintextBlock, 0, cbcBlockSize);

                // XOR unless is IV block.
                if(i > 0){
                    byte[] previousBlock = new byte[cbcBlockSize];
                    System.arraycopy(cyphertextAsBytes, i-cbcBlockSize, previousBlock, 0, cbcBlockSize);
                    for(int b = 0; b < cbcBlockSize; b++){
                        plaintextBlock[b] = (byte)(plaintextBlock[b] ^ previousBlock[b]);
                    }
                }

                // AES
                byte[] cyphertextBlock = cipher.doFinal(plaintextBlock);
                System.arraycopy(cyphertextBlock, 0, cyphertextAsBytes, i, cbcBlockSize);

            }

            String readable = Base64.getEncoder().encodeToString(cyphertextAsBytes);
            return readable;
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decoded = Base64.getDecoder().decode(strToDecrypt);
            byte[] decrypted = new byte[decoded.length];

            for(int i = 0; i < decrypted.length; i += cbcBlockSize){
                byte[] plaintextBlock = new byte[cbcBlockSize];
                System.arraycopy(decoded, i, plaintextBlock, 0, cbcBlockSize);

                // AES
                byte[] decryptedBlock = cipher.doFinal(plaintextBlock);

                // XOR unless is IV block.
                if(i != 0) {
                    byte[] previousBlock = new byte[cbcBlockSize];
                    System.arraycopy(decoded, i-cbcBlockSize, previousBlock, 0, cbcBlockSize);
                    for(int b = 0; b < cbcBlockSize; b++){
                        decryptedBlock[b] = (byte)(decryptedBlock[b] ^ previousBlock[b]);
                    }
                }

                if(i != 0){
                    System.arraycopy(decryptedBlock, 0, decrypted, i, cbcBlockSize);
                }
            }

            return new String(decrypted);
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
            return null;
        }
    }

    private void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
