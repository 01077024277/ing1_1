package com.example.pc.ing1_.Menu.Menu;

public class Food_list_item {
    String name,num,size,kcal;

    public Food_list_item(String name, String num, String size, String kcal) {
        this.name = name;
        this.num = num;
        this.size = size;
        this.kcal = kcal;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getKcal() {
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal = kcal;
    }
}
