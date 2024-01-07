package mangogo.appbase.util;


import android.text.TextUtils;
import android.util.Base64;

import java.io.FileOutputStream;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtils {

    public static final String KEY = "UIde98908765IO!@";
    public static final String IV = "#9T8feiueaJUPOEY";

    public static void decoderBase64File(String base64Code,String savePath) throws Exception {
        byte[] buffer =Base64.decode(base64Code, Base64.DEFAULT);
        FileOutputStream out = new FileOutputStream(savePath);
        out.write(buffer);
        out.close();
    }

    public static String encryptAES(String key, String iv, String content) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(content)){
            return "";
        }

        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE,
                    new SecretKeySpec(key.getBytes(), "AES"),
                    new IvParameterSpec(iv.getBytes("utf-8")));

            int blockSize = cipher.getBlockSize();
            byte[] byteEncode = content.getBytes("utf-8");
            if (byteEncode.length % blockSize != 0) {
                byte[] bytes = new byte[(byteEncode.length / blockSize + 1) * blockSize];
                System.arraycopy(byteEncode, 0, bytes, 0, byteEncode.length);
                byteEncode = bytes;
            }
            byte[] byteAes = cipher.doFinal(byteEncode);

            /*
             * Base64.DEFAULT：这个参数是默认，使用默认的方法来加密
             * Base64.NO_PADDING：这个参数是略去加密字符串最后的“=”
             * Base64.NO_WRAP：这个参数意思是略去所有的换行符
             * Base64.URL_SAFE：这个参数意思是加密时不使用对URL和文件名有特殊意义的字符来作为加密字符，具体就是以-和_取代+和/
             */
            return new String(Base64.encode(byteAes, Base64.NO_WRAP), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String decryptAES(String key, String iv, String content) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(content)){
            return "";
        }

        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE,
                    new SecretKeySpec(key.getBytes(), "AES"),
                    new IvParameterSpec(iv.getBytes("utf-8")));

            byte[] byteContent = Base64.decode(content, Base64.NO_WRAP);
            byte[] decryptBytes = cipher.doFinal(byteContent);
            int i = decryptBytes.length - 1;
            while (i >= 0 && decryptBytes[i] == 0) {
                i--;
            }
            i++;
            if (i != decryptBytes.length) {
                decryptBytes = Arrays.copyOf(decryptBytes, i);
            }

            return new String(decryptBytes, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

//        return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
//        return new String(Base64.encode(encrypted, Base64.NO_WRAP), "UTF-8");
        return new String(Base64.encode(encrypted, Base64.NO_WRAP), "UTF-8");
    }


    /**
     * AES加密
     *
     * @param data
     *            将要加密的内容
     * @param key
     *            密钥
     * @return 已经加密的内容
     */
    public static String encrypt(byte[] data, byte[] key) {
        //不足16字节，补齐内容为差值
        int len = 16 - data.length % 16;
        for (int i = 0; i < len; i++) {
            byte[] bytes = { (byte) len };
            data = ArrayUtils.concat(data, bytes);
        }
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            return  new String(Base64.encode(cipher.doFinal(data), Base64.NO_WRAP), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param data
     *            将要解密的内容
     * @param key
     *            密钥
     * @return 已经解密的内容
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        data = ArrayUtils.noPadding(data, -1);
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] decryptData = cipher.doFinal(data);
            int len = 2 + ByteUtils.byteToInt(decryptData[4]) + 3;
            return ArrayUtils.noPadding(decryptData, len);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[] {};
    }
}
