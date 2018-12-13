package ru.adkazankov.domain;

import annotation.Column;

public class Provider {

    @Column(name = "Тип", pKey = true)
    private String type;
    @Column(name = "Название")
    private String name;

    public Provider() {
    }

    public Provider(String type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Provider{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
