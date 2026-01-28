package com.platformcommons.platform.service.report.application.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class CryptoUtlity {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    @Value("${commons.platform.security.key:whoWonTheElectionsOf2024}")
    private String KEY;


    public String encrypt(String attribute) {
        String result = null;
        if(attribute != null) {
            try {
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
                byte[] encrypted = cipher.doFinal(attribute.getBytes());
                result = Base64.getEncoder().encodeToString(encrypted);
            } catch (Exception e) {
                throw new RuntimeException("Error encrypting data", e);
            }
        }
        return result;
    }

    public String decrypt(String dbData) {
        String result = null;
        if(dbData != null) {
            try {
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
                byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(dbData));
                result = new String(decrypted);
            } catch (Exception e) {
                //throw new RuntimeException("Error decrypting data", e);
                result = dbData;
            }
        }
        return result;
    }
}
