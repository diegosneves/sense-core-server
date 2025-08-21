package br.com.diegosneves.enums;

public enum UserProfile {

    ADMIN("Full Access");

    private final String description;

    UserProfile(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
