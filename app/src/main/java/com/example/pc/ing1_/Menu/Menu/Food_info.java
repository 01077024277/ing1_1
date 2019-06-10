package com.example.pc.ing1_.Menu.Menu;

public class Food_info{
    String name,size;
    int num;
    double cal,carb,protein,fat,chol,fiber,salt,potass;

    public Food_info(String name, String size, int num, double cal, double carb, double protein, double fat, double chol, double fiber, double salt, double potass) {
        this.name = name;
        this.size = size;
        this.num = num;
        this.cal = cal;
        this.carb = carb;
        this.protein = protein;
        this.fat = fat;
        this.chol = chol;
        this.fiber = fiber;
        this.salt = salt;
        this.potass = potass;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getCal() {
        return cal;
    }

    public void setCal(double cal) {
        this.cal = cal;
    }

    public double getCarb() {
        return carb;
    }

    public void setCarb(double carb) {
        this.carb = carb;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getChol() {
        return chol;
    }

    public void setChol(double chol) {
        this.chol = chol;
    }

    public double getFiber() {
        return fiber;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    public double getSalt() {
        return salt;
    }

    public void setSalt(double salt) {
        this.salt = salt;
    }

    public double getPotass() {
        return potass;
    }

    public void setPotass(double potass) {
        this.potass = potass;
    }
}
