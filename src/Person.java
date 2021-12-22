import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;



public class Person {

    private PrivateKey privateKey;
    private PublicKey  publicKey;
    private PublicKey  receivedPublicKey;
    private byte[]     secretKey;
    private String     secretMessage;


    /**
     * Method gets a message then encrypts it
     * @param message gets a message to encrypt
     * @param person gets a person whom message is encrypted
     */
    public void encryptAndSendMessage(final String message, final Person person) {

        try {
            final SecretKeySpec keySpec = new SecretKeySpec(secretKey, "DES");
            final Cipher        cipher  = Cipher.getInstance("DES/ECB/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            final byte[] encryptedMessage = cipher.doFinal(message.getBytes());

            person.receiveAndDecryptMessage(encryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method generates common secret key
     */
    public void generateCommonSecretKey() {

        try {
            final KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
            keyAgreement.init(privateKey);
            keyAgreement.doPhase(receivedPublicKey, true);

            secretKey = shortenSecretKey(keyAgreement.generateSecret());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method generate pair of keys
     */
    public void generateKeys() {

        try {
            final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
            keyPairGenerator.initialize(1024);

            final KeyPair keyPair = keyPairGenerator.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey  = keyPair.getPublic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method returns a public key
     * @return public key
     */
    public PublicKey getPublicKey() {

        return publicKey;
    }

    /**
     * Method receiving a message tgeb decrypts it
     * @param message gets a message to decrypt
     */
    public void receiveAndDecryptMessage(final byte[] message) {

        try {
            final SecretKeySpec keySpec = new SecretKeySpec(secretKey, "DES");
            final Cipher        cipher  = Cipher.getInstance("DES/ECB/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            secretMessage = new String(cipher.doFinal(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * In a real life example you must serialize the public key for transferring.
     *
     * @param  person gets a person whom we want to receive a publicKey
     */
    public void receivePublicKeyFrom(final Person person) {

        receivedPublicKey = person.getPublicKey();
    }

    /**
     * getting a secret message
     */
    public void whisperTheSecretMessage() {

        System.out.println(secretMessage);
    }

    /**
     * 1024 bit symmetric key size is so big for DES so we must shorten the key size. You can get first 8 longKey of the
     * byte array or can use a key factory
     *
     * @param   longKey gets a key
     *
     * @return returns a shortenedKey or null
     */
    private byte[] shortenSecretKey(final byte[] longKey) {

        try {
            final byte[] shortenedKey = new byte[8];

            System.arraycopy(longKey, 0, shortenedKey, 0, shortenedKey.length);

            return shortenedKey;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}