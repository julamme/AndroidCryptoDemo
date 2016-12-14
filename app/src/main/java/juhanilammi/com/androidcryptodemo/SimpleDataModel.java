package juhanilammi.com.androidcryptodemo;

import android.util.Log;

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

/**
 * Simple data model class for demo purpose
 */
public class SimpleDataModel {
    private CryptoController cryptController;

    private String plainString;
    private byte[] cryptedStringByteArray;
    private String encryptedString;


    public SimpleDataModel() {
        try {
            cryptController = new CryptoController("AES/ECB/PKCS5Padding");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * save a string and encrypt it. For demoing save also a plain version for comparing
     *
     * @param string String to save
     */
    public void saveStringAndEncrypt(String string) {
        plainString = string;
        try {
            cryptedStringByteArray = cryptController.encrypt(string);
            encryptedString = new String(cryptedStringByteArray, "UTF-8");
            Log.d("TEST", "saveStringAndEncrypt: ");
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public String getPlainString() {
        return plainString;
    }

    public String getEncryptedString() {
        return encryptedString;
    }

    public String decryptString(){
        String returable = "";
        try {
            returable= cryptController.decrypt(cryptedStringByteArray);
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return returable;
    }

    public void setPassword() {
    }

    public boolean insertPassword(String string) {
        try {
            cryptController.generateKey(string, 8, 655536);
            return true;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return false;
    }


    public String getCryptoDetails() {
        return cryptController.getDetails();
    }
}
