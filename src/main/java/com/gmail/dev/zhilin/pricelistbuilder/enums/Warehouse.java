package com.gmail.dev.zhilin.pricelistbuilder.enums;

public enum Warehouse {

    KHABAROVSK("г.Хабаровск"),
    NOVOSIBIRSK("г.Новосибирск"),
    MOSCOW("г.Москва");

    private String name;

    Warehouse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
