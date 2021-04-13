package Inl2.inl2.shared;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Component
public class Util {
    public String generateHash(String uniqueProperty) {
        try {
            MessageDigest hashFunc = MessageDigest.getInstance("SHA3-256");
            byte[] digest = hashFunc.digest(uniqueProperty.getBytes());
            return Arrays.toString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
