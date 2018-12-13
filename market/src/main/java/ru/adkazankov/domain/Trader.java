package ru.adkazankov.domain;

import annotation.Column;

public class Trader {

    @Column(name = "ФИО", pKey = true)
    private String fio;
    @Column(name = "Город", pKey = true)
    private String city;
    @Column(name = "Название", pKey = true)
    private String name;
    @Column(name = "Цена")
    private Double cost;
    @Column(name = "Количество позиций")
    private Integer count;

    public Trader() {
    }

    public Trader(String fio, String city, String name, Double cost, Integer count) {
        this.fio = fio;
        this.city = city;
        this.name = name;
        this.cost = cost;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Trader{" +
                "fio='" + fio + '\'' +
                ", city='" + city + '\'' +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", count=" + count +
                '}';
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
