package com.securevault;

import com.securevault.models.*;
import com.securevault.services.*;
import com.securevault.utils.PasswordGenerator;
import java.util.*;

public class Main {
    private static Vault vault;
    private static Scanner scanner = new Scanner(System.in);

    private static void autoSave() {
        try {
            VaultService.saveVault(vault, AuthService.getMasterPasswordHash());
        } catch (IOException e) {
            System.out.println("⚠️ Background save failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (!AuthService.authenticate()) {
            System.out.println("Program terminated.");
            return;
        }

        try {
            vault = VaultService.loadVault(AuthService.getMasterPasswordHash());
            System.out.println("📂 Vault loaded. Entries: " + vault.getCount());
        } catch (SecurityException e) {
            System.out.println(e.getMessage());
            return;
        } catch (IOException e) {
            System.out.println("⚠️ Failed to load. Starting empty.");
            vault = new Vault();
        }

        boolean running = true;
        while (running) {
            System.out.println("\n==============================");
            System.out.println("   SECURE VAULT MANAGER");
            System.out.println("   Total Entries: " + vault.getCount());
            System.out.println("==============================");
            System.out.println("[1] View Credentials");
            System.out.println("[2] Add New Credential");
            System.out.println("[3] Search Credential");
            System.out.println("[4] Delete Credential");
            System.out.println("[5] Generate Strong Password");
            System.out.println("[6] Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    List<Credential> all = vault.getAllCredentials();
                    if (all.isEmpty()) System.out.println("\n📭 Vault is empty.");
                    else {
                        System.out.println("\n--- YOUR ENCRYPTED VAULT ---");
                        all.forEach(System.out::println);
                    }
                    break;

                case "2":
                    System.out.print("Enter Service Name: ");
                    String service = scanner.nextLine().trim();
                    if (service.isEmpty()) { System.out.println("❌ Service cannot be empty."); break; }
                    if (vault.getCredential(service) != null) {
                        System.out.println("⚠️ Service already exists.");
                        break;
                    }
                    System.out.print("Enter Username: ");
                    String username = scanner.nextLine().trim();
                    if (username.isEmpty()) { System.out.println("❌ Username cannot be empty."); break; }
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine().trim();
                    if (password.isEmpty()) { System.out.println("❌ Password cannot be empty."); break; }
                    
                    vault.addCredential(new Credential(service, username, password));
                    System.out.println("✅ Credential added securely!");
                    autoSave();
                    break;

                case "3":
                    System.out.print("Enter Service Name to search: ");
                    Credential found = vault.getCredential(scanner.nextLine().trim());
                    if (found != null) {
                        System.out.println("\n🔍 Found:");
                        System.out.println(found);
                    } else {
                        System.out.println("❌ Not found.");
                    }
                    break;

                case "4":
                    System.out.print("Enter Service Name to delete: ");
                    if (vault.removeCredential(scanner.nextLine().trim())) {
                        System.out.println("✅ Deleted.");
                        autoSave();
                    } else {
                        System.out.println("❌ Not found.");
                    }
                    break;

                case "5":
                    System.out.println("🔑 Generated Password: " + PasswordGenerator.generate(12));
                    break;

                case "6":
                    System.out.println("🔒 Vault closed. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }
}
