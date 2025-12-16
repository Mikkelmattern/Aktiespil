package model;

import java.text.DecimalFormat;

public class Stock {
    private final String name;
    private final StockType type;
    private double price;
    DecimalFormat df = new DecimalFormat("#.##");

    public Stock(String name, StockType type, double price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public StockType getType() {
        return type;
    }

    public double getPrice() {
        return Double.parseDouble(df.format(price));
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " (" + type + ") - Pris: " + getPrice();
    }

}
