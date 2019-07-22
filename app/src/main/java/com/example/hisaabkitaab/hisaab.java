package com.example.hisaabkitaab;

public class hisaab {
    private String name,note,phone,date;
    private int money;

    public hisaab(String name, String phone, int money, String date, String note) {
        this.name = name;
        this.note = note;
        this.phone = phone;
        this.date = date;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate() {
        return date;
    }

    public int getMoney() {
        return money;
    }
}
