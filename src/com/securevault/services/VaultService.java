package com.securevault.services;

import com.securevault.models.*;
import java.io.*;
import java.nio.file.*;

public class VaultService {
    private static final String FILE_PATH = "data/vault.dat";

    public static void saveVault(Vault vault, String masterPasswordHash) throws IOException {
        Files.createDirectories(Paths.get("data"));
        StringBuilder sb = new StringBuilder();
        sb.append(masterPasswordHash).append("\n");
        
        for (Credential c : vault.getAllCredentials()) {
            String encService = CryptoService.encrypt(c.getService());
            String encUser = CryptoService.encrypt(c.getUsername());
            String encPass = CryptoService.encrypt(c.getPassword());
            sb.append(encService).append("|").append(encUser).append("|").append(encPass).append("\n");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(sb.toString());
        }
    }

    public static Vault loadVault(String inputMasterHash) throws IOException, SecurityException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new Vault();
        }

        Vault vault = new Vault();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String storedHash = reader.readLine();
            if (storedHash == null) throw new IOException("Corrupted file.");
            
            if (!storedHash.equals(inputMasterHash)) {
                throw new SecurityException("❌ Invalid Master Password! Access Denied.");
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String service = CryptoService.decrypt(parts[0]);
                    String username = CryptoService.decrypt(parts[1]);
                    String password = CryptoService.decrypt(parts[2]);
                    vault.addCredential(new Credential(service, username, password));
                }
            }
        }
        return vault;
    }
}
