package com.securevault.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CryptoService {
    private static String SECRET_KEY = null;

    static {
        try {
          
            String envContent = new String(Files.readAllBytes(Paths.get(".env")));
            for (String line : envContent.split("\n")) {
                if (line.startsWith("VAULT_SECRET_KEY=")) {
                    SECRET_KEY = line.split("=", 2)[1].trim();
                    break;
                }
            }
            if (SECRET_KEY == null) {
                throw new RuntimeException("VAULT_SECRET_KEY not found in .env file!");
            }
        } catch (IOException e) {
            System.err.println("CRITICAL ERROR: Could not load .env file.");
            System.err.println("Make sure .env exists and contains VAULT_SECRET_KEY.");
            System.exit(1);
        }
    }

    public static String encrypt(String data) {
        char[] chars = data.toCharArray();
        char[] keyChars = SECRET_KEY.toCharArray();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            result.append((char) (chars[i] ^ keyChars[i % keyChars.length]));
        }
        return result.toString();
    }

    public static String decrypt(String encryptedData) {
        return encrypt(encryptedData);
    }
}
