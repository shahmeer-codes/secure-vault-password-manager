
package com.securevault.models;

public class Credential {
    private final String service;
    private final String username;
    private final String password;

    public Credential(String service, String username, String password) {
        this.service = service;
        this.username = username;
        this.password = password;
    }

    public String getService() { return service; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    @Override
    public String toString() {
        return String.format("🌐 Service: %-20s | 👤 Username: %-15s | 🔑 Password: %s", 
                             service, username, password);
    }
}
