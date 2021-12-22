import java.util.Scanner;

/**
 * @author Wojciech Piórecki
 * Diffie-Hellman
 */
public class Main {

    public static void main(final String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        new Main().init(message);
    }

    private void init(String message) {
        final Person alice = new Person();
        final Person bob   = new Person();
        alice.generateKeys();
        bob.generateKeys();
        alice.receivePublicKeyFrom(bob);
        bob.receivePublicKeyFrom(alice);
        alice.generateCommonSecretKey();
        bob.generateCommonSecretKey();
        alice.encryptAndSendMessage(message, bob);
        bob.whisperTheSecretMessage();

    }
}