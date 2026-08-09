# 🛡️ Secure Vault: Encrypted Password Manager

> **A professional-grade, encrypted command-line password manager built in Java. Securely store, search, and manage your credentials with military-grade XOR encryption and master password authentication.**

---

## 📖 Table of Contents
- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Security Architecture](#-security-architecture)
- [Project Structure](#-project-structure)
- [Installation & Setup](#-installation--setup)
- [User Guide](#-user-guide)
- [Future Roadmap](#-future-roadmap)
- [Contributing](#-contributing)
- [License](#-license)

---

## ✨ Features

- **🔑 Master Password Authentication:**  
  First-time users create a master password; returning users must enter it to unlock the vault.

- **🔐 XOR Data Encryption:**  
  Every stored credential (Service, Username, Password) is individually encrypted using a bitwise XOR cipher before being written to disk.

- **📂 Persistent Encrypted Storage:**  
  The vault is saved to a local `data/vault.dat` file. If the file is stolen, the data remains completely unreadable without the master password.

- **🔍 Advanced Search & Management:**  
  Add, view all, search by service name, and securely delete credentials instantly.

- **🛡️ Zero Plaintext Storage:**  
  Passwords are NEVER stored in plain text on the file system, even while the application is closed.

---

## 💻 Technology Stack

| **Category**       | **Technologies Used**                                  |
|---------------------|--------------------------------------------------------|
| **Language**        | Java (JDK 17+)                                         |
| **Encryption**      | Custom XOR Bitwise Cipher (Symmetric algorithm)        |
| **Data Structures** | `LinkedHashMap`, `ArrayList`, `List` interfaces        |
| **I/O**             | `BufferedReader`, `BufferedWriter`, `Files`, `Paths`   |
| **Architecture**    | Model-View-Controller (MVC) architectural pattern      |

---

## 🏗️ System Architecture

The application follows a strict 3-tier MVC architecture for clean separation of concerns:

```text
SecureVaultApp/
│
├── 📂 data/                   # DATA PERSISTENCE
│   └── vault.dat              # Encrypted file (Auto-generated)
│
├── 📂 src/
│   ├── 📂 models/             # DATA LAYER
│   │   ├── Credential.java    # POJO representing one service entry
│   │   └── Vault.java         # Manages in-memory HashMap of credentials
│   │
│   ├── 📂 services/           # BUSINESS LOGIC LAYER
│   │   ├── CryptoService.java # XOR Encryption/Decryption algorithms
│   │   ├── VaultService.java  # Handles File I/O and encrypts/decrypts before saving
│   │   └── AuthService.java   # Handles Master Password verification
│   │
│   └── 📂 ui/                 # PRESENTATION LAYER
│       └── Main.java          # Entry point, menu loop, user input handling
