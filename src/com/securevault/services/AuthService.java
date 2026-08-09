package com.securevault.services;

import java.io.*;
import java.util.Scanner;

public class AuthService {
    private static String masterPasswordHash = null;
    private static final String FILE_PATH = "data/vault.dat";

    public static boolean authenticate() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n==================================");
        System.out.println("   🛡️ SECURE VAULT LOGIN");
        System.out.println("==================================");
        
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                System.out.println("\n🆕 FIRST TIME SETUP");
                System.out.print("Create a Master Password: ");
                String newPass = sc.nextLine();
                masterPasswordHash = CryptoService.encrypt(newPass);
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                    writer.write(masterPasswordHash + "\n");
                }
                System.out.println("✅ Vault initialized.");
                return true;
            } else {
                System.out.print("Enter your Master Password: ");
                String inputPass = sc.nextLine();
                String inputHash = CryptoService.encrypt(inputPass);
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String storedHash = reader.readLine();
                    if (storedHash != null && storedHash.equals(inputHash)) {
                        masterPasswordHash = storedHash;
                        System.out.println("✅ Access Granted!");
                        return true;
                    } else {
                        System.out.println("❌ Access Denied!");
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("❌ System Error: " + e.getMessage());
            return false;
        }
    }

    public static String getMasterPasswordHash() {
        return masterPasswordHash;
    }
}
