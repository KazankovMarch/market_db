package ru.adkazankov.domain;

import annotation.Column;

import java.util.Date;

public class Registration {

    @Column(name = "ФИО", pKey = true)
    private String fio;
    @Column(name = "Дата рег.")
    private Date date;
    @Column(name = "Кол-во позиций")
    private Integer tradeCount;

    public Registration() {
    }

    public Registration(String fio, Date date, Integer tradeCount) {
        this.fio = fio;
        this.date = date;
        this.tradeCount = tradeCount;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "fio='" + fio + '\'' +
                ", date=" + date +
                ", tradeCount=" + tradeCount +
                '}';
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(Integer tradeCount) {
        this.tradeCount = tradeCount;
    }
}
