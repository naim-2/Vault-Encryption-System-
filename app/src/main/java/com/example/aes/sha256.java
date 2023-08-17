package com.example.aes;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class sha256 {
    public static byte[] SHA(String input) throws NoSuchAlgorithmException
    {
        MessageDigest md1 = MessageDigest.getInstance("SHA-256");
        return md1.digest(input.getBytes(StandardCharsets.UTF_8));
    }
    public static String HexString(byte[] hash)
    {
        BigInteger n = new BigInteger(1, hash);
        StringBuilder newhexString = new StringBuilder(n.toString(16));
        while (newhexString.length() < 32)
            newhexString.insert(0, '0');
        return newhexString.toString();
    }
    public static String shathis(String giveText)
    {
        try
        {
            return HexString(SHA(giveText));
        }
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }
        return "";
    }
}