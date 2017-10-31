public class KeyAndCiphertext {
    private String key;
    private String ciphertext;

    public KeyAndCiphertext(){}

    public KeyAndCiphertext(String key, String ciphertext){
        this.key = key;
        this.ciphertext = ciphertext;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }
}
