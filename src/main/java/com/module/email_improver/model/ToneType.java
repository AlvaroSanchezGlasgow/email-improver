package com.module.email_improver.model;

public enum ToneType {
    PROFESSIONAL("Profesional"),
    FRIENDLY("Cercano"),
    DIRECT("Directo"),
    FORMAL("Formal"),
    CASUAL("Casual");

    private final String displayName;

    ToneType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
