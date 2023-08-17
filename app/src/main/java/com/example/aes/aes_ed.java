package com.example.aes;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class aes_ed{

    private static SecretKeySpec sk;
    private static byte[] k;

    public static void setKey(final String myK) {
        MessageDigest sha = null;
        try {
            k = myK.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            k = sha.digest(k);
            k = Arrays.copyOf(k, 16);
            sk = new SecretKeySpec(k, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(final String encryptThis, final String s) {
        try {
            setKey(s);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, sk);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(encryptThis.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(final String decryptThis, final String s) {
        try {
            setKey(s);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, sk);
            return new String(cipher.doFinal(Base64.getDecoder()
                    .decode(decryptThis)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public static String aes_e(String giveText, final String sk) {
        return aes_ed.encrypt(giveText, sk) ;
    }

    public static String aes_d(String giveCipher, final String sk) {
        return aes_ed.decrypt(giveCipher, sk) ;
    }
}