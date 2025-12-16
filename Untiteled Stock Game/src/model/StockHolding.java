package model;

public class StockHolding {
    Stock stock;
    int date;
    int amount;
    double averageBuyPrice;

    StockHolding(Stock stock, int amount, double averageBuyPrice) {
        this.stock = stock;
        this.amount = amount;
        this.averageBuyPrice = averageBuyPrice;
    }

    public Stock getStock() {
        return stock;
    }

    public void addAmount(int amount, double price) {
        this.amount += amount;
        this.averageBuyPrice += price;
    }
    public void removeAmount(int amount) {
        this.amount -= amount;
    }

    int getDate() {
        return date;
    }

    void setDate(int date) {
        this.date = date;
    }

    public void addToDate(int date) {
        this.date += date;
    }

    int getAmount() {
        return amount;
    }

    void setAmount(int amount) {
    }


    @Override
    public String toString() {
        return stock.getName() + " (" + stock.getType() + ")"
                + " | Gennemsnitskøbspris: "+ averageBuyPrice
                + " | Nu: " + stock.getPrice()
                + " | Mængde: " + amount;
    }
}
