package logic;

import model.Stock;

import java.util.ArrayList;
import java.util.List;

public class Market {
    List<Stock> stocks = new ArrayList<>();

    int currentDay;

    void addStock(Stock stock) {
        stocks.add(stock);
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    private void nextDay() {
        currentDay++;
    }
}
