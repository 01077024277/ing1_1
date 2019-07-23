package com.example.pc.ing1_.Menu.Menu;

import java.io.Serializable;

public class Food_info2 implements Serializable {
    //횟수 혹은 g
    int num;
    //사이즈에 g표시가없을떄 임의로 약 ~g 표시
    double g;
    Food_info food_info;
    //아,점,저
    String time;

    public Food_info2(int num, double g, Food_info food_info) {
        this.num = num;
        this.g = g;
        this.food_info = food_info;
    }

    public Food_info2(int num, double g, String time, Food_info food_info) {
        this.num = num;
        this.g = g;
        this.food_info = food_info;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Food_info getFood_info() {
        return food_info;
    }

    public void setFood_info(Food_info food_info) {
        this.food_info = food_info;
    }
}
