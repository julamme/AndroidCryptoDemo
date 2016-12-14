package juhanilammi.com.androidcryptodemo.modules.crypto;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * CryptoController for simple encryption demo
 */

public class CryptoController {
    private static final String TAG = CryptoController.class.getName();
    private Cipher cipher;
    private SecretKey key;
    private static final int KEY_SIZE = 128;
    private AlgorithmParameters algorithmParameters;
    private final String SECRETFACTORY_ALGORITHM = "PBKDF2withHmacSHA1";

    //FOR DEMO PURPOSE
    private int seedForPrint;
    private int iterationsForPrint;

    public CryptoController(String transformation) throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipher = Cipher.getInstance(transformation);
    }

    /**
     * @param input         password
     * @param seedCount     SecureRandom seed count
     * @param iterations    Number of iterations
     * @param KeyAlgorithm  SecretKey algorithm
     * @param KeySize       Key length
     * @param keySpecString Key spec type as string
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public void generateKeyWithParams(String input, int seedCount, int iterations, String KeyAlgorithm, int KeySize, String keySpecString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] inputCharArray = input.toCharArray();
        final byte[] salt = SecureRandom.getSeed(seedCount);
        seedForPrint = seedCount;
        iterationsForPrint = iterations;
        SecretKeyFactory factory = SecretKeyFactory.getInstance(KeyAlgorithm);
        KeySpec keySpec = new PBEKeySpec(inputCharArray, salt, iterations, KeySize);
        SecretKey tmp = factory.generateSecret(keySpec);
        key = new SecretKeySpec(tmp.getEncoded(), keySpecString);
    }

    /**
     * @param input      Password
     * @param seedCount  SecureRandom seed count
     * @param iterations Number of Iterations
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public void generateKey(String input, int seedCount, int iterations) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] inputCharArray = input.toCharArray();
        final byte[] salt = SecureRandom.getSeed(seedCount);
        seedForPrint = seedCount;
        iterationsForPrint = iterations;
        SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRETFACTORY_ALGORITHM);
        KeySpec keySpec = new PBEKeySpec(inputCharArray, salt, iterations, KEY_SIZE);
        SecretKey tmp = factory.generateSecret(keySpec);
        key = new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    /**
     * @param string String to encrypt
     * @return byte array of encrypted String
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     */
    public byte[] encrypt(String string) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        algorithmParameters = cipher.getParameters();
        final byte[] bytes = string.getBytes("UTF-8");
        return cipher.doFinal(bytes);
    }

    /**
     * @param encryptedByteArray byte array of encrypted String
     * @return
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws InvalidParameterSpecException
     * @throws UnsupportedEncodingException
     */
    public String decrypt(byte[] encryptedByteArray) throws BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidParameterSpecException, UnsupportedEncodingException {
        cipher.init(Cipher.DECRYPT_MODE, key);
        algorithmParameters = cipher.getParameters();
        String decryptedString = new String(cipher.doFinal(encryptedByteArray), "UTF-8");
        return decryptedString;
    }

    //FOR DEMO
    public String getDetails() {
        return "SecretKeyFactory algorithm: " + SECRETFACTORY_ALGORITHM + " \r\n" + "salt bytes count: " + seedForPrint + " \r\n" +
                "Iterations " + iterationsForPrint + "\r\n" + "Key length: " + KEY_SIZE + "\r\n" + "Cipher algorithm: " + cipher.getAlgorithm();
    }
}
