import java.util.*;
import java.math.BigInteger;
import java.lang.*;

/**
 * @author Wojciech Piórecki
 * RSA
 */
class RSA{
    public static void main (String args[]){
        Random rand1 = new Random(System.currentTimeMillis());
        Random rand2 = new Random(System.currentTimeMillis()*10);

        int pubKey = 2;

        BigInteger p = BigInteger.probablePrime(32, rand1);
        BigInteger q = BigInteger.probablePrime(32, rand2);

//multiplying p and q
        BigInteger n = p.multiply(q);

        BigInteger p_1 = p.subtract(new BigInteger("1"));
        BigInteger q_1 = q.subtract(new BigInteger("1"));

        BigInteger z = p_1.multiply(q_1);

        while (true) {
            BigInteger GCD = z.gcd(new BigInteger(""+pubKey));
            if(GCD.equals(BigInteger.ONE)){
                break;
            }
            pubKey++;
        }

        BigInteger big_pubkey = new BigInteger(""+pubKey);
        BigInteger prvkey = big_pubkey.modInverse(z);
        System.out.println("'public key "+pubKey+","+n);
        System.out.println("'private key "+prvkey+","+n);

//RSA encryption and decryption
        Scanner sc = new Scanner(System.in);
        System.out.println("enter the message to be encrypted");
        String msg = sc.nextLine();

        byte[] bytes = msg.getBytes();

        for (int i=0; i<msg.length();i++){

            int asciiVal = bytes[i];
            BigInteger val;
            val = new BigInteger(String.valueOf(asciiVal));

            BigInteger cipherVal = val.modPow(big_pubkey, n);
            System.out.println("cipher text: "+cipherVal);

            BigInteger plainVal = cipherVal.modPow(prvkey, n);
            int i_plainVal = plainVal.intValue();

            System.out.println("plain text: "+Character.toString(i_plainVal));
        }
    }
}