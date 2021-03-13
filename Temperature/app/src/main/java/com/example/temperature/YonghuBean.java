package com.example.temperature;

public class YonghuBean {
    public String name;
    public String num;
    public String banji;
    public String phone;
    public YonghuBean(){}
    public YonghuBean(String name, String num, String banji, String phone) {
        this.name = name;
        this.num = num;
        this.banji = banji;
        this.phone = phone;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getBanji() {
        return banji;
    }

    public void setBanji(String banji) {
        this.banji = banji;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

