package model;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    List<StockHolding> holdings = new ArrayList<>();

    private void addHoldings(StockHolding holding) {
        holdings.add(holding);
    }

    public void addHolding(Stock stock, int amount, double price) {
        for (StockHolding holding : holdings) {
            if (holding.getStock().equals(stock)) ;
            holding.addAmount(amount, price);
        }
        addHoldings(new StockHolding(stock, amount, price));
    }
    public void removeHolding(Stock stock, int amount) {
       for  (StockHolding holding : holdings) {
           if (holding.getStock().equals(stock) || holding.getAmount() >= amount) {
               holding.removeAmount(amount);

           }
       }
    }
    public void removeHoldings(StockHolding holding) {
        holdings.remove(holding);
    }

    public List<StockHolding> getHoldings() {
        return holdings;
    }
}
