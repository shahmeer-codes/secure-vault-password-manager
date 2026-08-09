package com.securevault.models;

import java.util.*;

public class Vault {
    private final Map<String, Credential> credentials;

    public Vault() {
        this.credentials = new LinkedHashMap<>();
    }

    public void addCredential(Credential cred) {
        credentials.put(cred.getService().toLowerCase(), cred);
    }

    public boolean removeCredential(String service) {
        return credentials.remove(service.toLowerCase()) != null;
    }

    public Credential getCredential(String service) {
        return credentials.get(service.toLowerCase());
    }

    public List<Credential> getAllCredentials() {
        return new ArrayList<>(credentials.values());
    }

    public int getCount() {
        return credentials.size();
    }
}
