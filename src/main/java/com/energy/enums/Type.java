package com.energy.enums;

public enum Type {
    IMPORT("import"),
    PUSH("push"),
    BILLING("billing");

    String type;

    Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
