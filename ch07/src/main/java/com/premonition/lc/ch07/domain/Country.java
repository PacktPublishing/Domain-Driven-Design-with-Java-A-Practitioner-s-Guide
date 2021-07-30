package com.premonition.lc.ch07.domain;

public enum Country {
    USA("US"), INDIA("IN"), WAKANDA("WK"), SOKOVIA("SK");

    private final String code;

    Country(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
