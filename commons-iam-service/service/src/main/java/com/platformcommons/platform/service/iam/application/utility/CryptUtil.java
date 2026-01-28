package com.platformcommons.platform.service.iam.application.utility;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.util.Base64;

@Component
public class CryptUtil implements AttributeConverter<String, String> {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final byte[] KEY = "platformcommons1".getBytes();

    @Override
    public String convertToDatabaseColumn(String attribute) {
        String result = null;
        if(attribute != null) {
            try {
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                SecretKeySpec keySpec = new SecretKeySpec(KEY, "AES");
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
                byte[] encrypted = cipher.doFinal(attribute.getBytes());
                result = Base64.getEncoder().encodeToString(encrypted);
            } catch (Exception e) {
                throw new RuntimeException("Error encrypting data", e);
            }
        }
        return result;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        String result = null;
        if(dbData != null) {
            try {
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                SecretKeySpec keySpec = new SecretKeySpec(KEY, "AES");
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