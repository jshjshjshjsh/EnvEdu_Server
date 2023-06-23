package com.example.demo.admin.cipher;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Component
public class AdminCipher {
    /**
     * 어드민 로그인에 사용되는 암호화 클래스
     */

    @Value("${spring.cipher.key}")
    private String secretKey;

    /**
     * 암호화 알고리즘, ex. DES, AES
     */
    @Value("${spring.cipher.transformation}")
    private String transformation;

    /**
     * 사용하는 DBMS에서 사용하는 암호화와 맞춘 암호화 메소드
     */
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
        } catch (Exception e) {
            return target;
        }
    }
}
