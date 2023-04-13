package com.example.demo.admin.cipher;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class AdminCipher {
    @Value("${spring.cipher.key}")
    private String secretKey;

    @Value("${spring.cipher.transformation")
    private String transformation;

    public String encrypt(String target) {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            byte[] key = new byte[16];
            int i = 0;

            for(byte b : secretKey.getBytes()) {
                key[i++%16] ^= b;
            }

            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, transformation));
            return new String(Hex.encodeHex(cipher.doFinal(target.getBytes(StandardCharsets.UTF_8)))).toUpperCase();
        } catch(Exception e) {
            return target;
        }
    }
}
