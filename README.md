# 🛡️ Secure Vault: Encrypted Password Manager

> An enterprise-grade, encrypted CLI password manager built in Java. Uses external environment variables (`VAULT_SECRET_KEY`) for cryptographic security, master password authentication, and automatic file persistence.

---

## 📖 Table of Contents
- [Key Features](#-key-features)
- [Security Architecture](#-security-architecture)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [Installation & Setup](#-installation--setup)
- [User Guide](#-user-guide)
- [Future Roadmap](#-future-roadmap)
- [Contributing](#-contributing)
- [License](#-license)

---

## ✨ Key Features

- **🔑 Master Password Authentication**  
  A unique master password acts as the encryption key to unlock the entire vault.

- **🔐 XOR Data Encryption**  
  Every credential (Service, Username, Password) is individually encrypted using a bitwise XOR cipher before being written to disk.

- **📂 External Environment Configuration**  
  The encryption secret key is stored in a local `.env` file. This follows industry best practices to keep sensitive data **out of source control**.

- **⚙️ Auto-Save Mechanism**  
  No manual saving required. The application instantly encrypts and writes data to `data/vault.dat` the moment a credential is added or deleted.

- **🔍 Full CRUD Operations**  
  Add, View All, Search by service name, and securely delete credentials.

- **🎲 Built-in Secure Password Generator**  
  Generates cryptographically strong 12-character passwords (includes uppercase, lowercase, numbers, and special symbols).

---

## 🔐 Security Architecture

Unlike beginner password managers, this application separates the encryption key from the source code.

| Layer | How it Works |
| :--- | :--- |
| **The Key** | Stored as `VAULT_SECRET_KEY` inside a `.env` file in the project root. |
| **The Source Code** | `CryptoService.java` reads the key from `.env` at runtime. The key is **never hardcoded** in the Java files. |
| **The Vault** | Data is serialized to `data/vault.dat` as a text file, but the contents are completely unreadable without the key. |
| **.gitignore** | Configured to explicitly ignore `.env` and `data/` folders, preventing accidental secret leaks to GitHub. |

---

## 💻 Technology Stack

| **Category**       | **Technologies Used**                                      |
|---------------------|------------------------------------------------------------|
| **Language**        | Java (JDK 17+)                                             |
| **Encryption**      | Custom XOR Bitwise Cipher (Symmetric Algorithm)            |
| **Configuration**   | `.env` Environment Variables                               |
| **Data Structures** | `LinkedHashMap`, `ArrayList`, `List`                       |
| **I/O**             | `BufferedReader`, `BufferedWriter`, `Files`, `Paths`       |
| **Architecture**    | Model-View-Controller (MVC) Design Pattern                 |

---

## 🏗️ Project Structure

```text
SecureVaultApp/
│
├── .env                      # [IMPORTANT] Contains VAULT_SECRET_KEY
├── .gitignore                # Ensures .env and data/ are NOT uploaded to GitHub
├── LICENSE
├── README.md
│
├── data/
│   └── vault.dat             # Encrypted data (Auto-generated on first run)
│
└── src/
    └── com.securevault/
        ├── Main.java         # Entry Point & UI Loop
        │
        ├── models/           # Data Entities
        │   ├── Credential.java
        │   └── Vault.java
        │
        ├── services/         # Business Logic
        │   ├── CryptoService.java    # Reads .env to encrypt/decrypt
        │   ├── AuthService.java      # Handles master password login
        │   └── VaultService.java     # Handles file I/O & auto-saving
        │
        └── utils/            # Helper Tools
            └── PasswordGenerator.java
