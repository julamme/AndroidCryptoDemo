package juhanilammi.com.androidcryptodemo;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import juhanilammi.com.androidcryptodemo.modules.crypto.CryptoController;

import static org.junit.Assert.assertEquals;

/**
 * Created by Laemmi on 14.12.2016.
 */

public class CryptoTest {

    public CryptoTest(){}
    @Test
    public void encryptionTest(){
        try {
            String testClause = "this is a test clause";
            CryptoController cryptoController = new CryptoController("AES/ECB/PKCS5Padding");
            cryptoController.generateKey("testkey",  8, 655536);
            byte[] bytes =cryptoController.encrypt(testClause);
            String s = new String(bytes);



            assertEquals(cryptoController.decrypt(bytes), testClause);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }
}
