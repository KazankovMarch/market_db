package ru.adkazankov.domain;

import java.util.Date;

public class Registration {

    private String fio;
    private Date date;
    private Integer TradeCount;

    public Registration() {
    }

    public Registration(String fio, Date date, Integer tradeCount) {
        this.fio = fio;
        this.date = date;
        TradeCount = tradeCount;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "fio='" + fio + '\'' +
                ", date=" + date +
                ", TradeCount=" + TradeCount +
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
        return TradeCount;
    }

    public void setTradeCount(Integer tradeCount) {
        TradeCount = tradeCount;
    }
}
