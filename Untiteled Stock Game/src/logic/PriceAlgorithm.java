package logic;

import model.Stock;
import java.lang.Math;
import java.text.DecimalFormat;

public class PriceAlgorithm {

    public void doNextDay(Market market) {

        for  (Stock stock : market.getStocks()) {
            double random = Math.random();

            stock.setPrice(stock.getPrice()*(random+0.55));

        }
    }
}
