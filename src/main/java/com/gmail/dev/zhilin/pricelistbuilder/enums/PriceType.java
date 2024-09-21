package com.gmail.dev.zhilin.pricelistbuilder.enums;

public enum PriceType {
    WHOLESALE("оптовые"),
    RETAIL("розничные");
    
    private String name;

    private PriceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
