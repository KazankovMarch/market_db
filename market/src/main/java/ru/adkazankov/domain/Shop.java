package ru.adkazankov.domain;

import annotation.Column;

public class Shop {

    @Column(name = "Имя", pKey = true)
    private String name;
    @Column(name = "Тип")
    private String type;

    public Shop() {
    }

    public Shop(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
