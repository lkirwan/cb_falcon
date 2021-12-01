package com.custombananas.model;

public enum ImageStatus {
    CONVERTED("CONVERTED"), UPLOADED("UPLOADED");

    private final String value;

    ImageStatus(String value) {
        this.value = value;
    }

    public static ImageStatus fromValue(String value) {
        if (value != null) {
            for (ImageStatus color : values()) {
                if (color.value.equals(value)) {
                    return color;
                }
            }
        }
        throw new IllegalArgumentException("Invalid status: " + value);
    }

    public String toValue() {
        return value;
    }
}
