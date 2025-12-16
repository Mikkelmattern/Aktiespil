package logic;

import model.*;
import util.FileHandler;
import util.TextUI;

import java.util.Scanner;

public class GameState {
    TextUI textUI = new TextUI();
    Scanner sc = new Scanner(System.in);
    FileHandler fh = new FileHandler();
    private User user;
    private boolean running = true;
    private boolean loggedIn = false;
    private final Market market = new Market();
    private final Portfolio portfolio = new Portfolio();
    private final PriceAlgorithm algorithm = new PriceAlgorithm();

    public void startGame() {
        initialzeMarket();
        while (running) {
            if (!loggedIn) {
                showLoginMenu();
            } else {
                showGameMenu();
            }
        }

    }

    public boolean showLoginMenu() {
        textUI.displayMsg("==Login eller registrer==", "1: Login", "2: Registrer", "3: Exit");
        int choice = Integer.parseInt(sc.nextLine());
        switch (choice) {
            case 1 -> loggedIn = doLogin();
            case 2 -> doRegister();
            case 3 -> {
                return false;
            }
            default -> System.out.println("Ugyldigt valg");
        }

        return false;
    }

    private void showGameMenu() {
        int choice = textUI.promptNumeric("==Game Menu==","Balance: "+ user.getBalance(), "", "1: Se marked", "2: Se portfolio", "3: Køb aktie", "4: Sælg aktie", "5: Næste dag", "6: Gem og logud");
        switch (choice) {
            case 1 -> showMarket();
            case 2 -> showPortfolio();
            case 3 -> buyStock();
            case 4 -> sellStock();
            case 5 -> nextDay();
            case 6 -> logout();
            default -> throw new IllegalStateException("Unexpected value: " + choice);
        }
    }


    /**
     * Login-flow.
     * <ol>
     *   <li>Prompt for brugernavn og adgangskode</li>
     *   <li>Valider via {@link User#authenticateUser(String, String, FileHandler)} (statisk)</li>
     *   <li>Ved succes: opret aktiv {@link User} og {@link Portfolio}, opret evt. brugerens mapper/filer</li>
     * </ol>
     *
     * @return {@code true} ved succesfuldt login, ellers {@code false}
     */
    public boolean doLogin() {
        String usernameInput = textUI.promptText("Skriv brugernavn");
        String passwordInput = textUI.promptText("Skriv adgangskode");

        if (User.authenticateUser(usernameInput, passwordInput, fh)) {
            textUI.displayMsg("Login successful!");
           user = new User(fh, usernameInput, passwordInput);
            user.createUserFiles();
            user.initializeBalance();
            return true;
        }
        textUI.displayMsg("Brugernavn eller password forkert!");
        return false;
    }

    /**
     * Registrerings-flow.
     * <ol>
     *   <li>Prompt for brugernavn og adgangskode</li>
     *   <li>Forsøg at oprette via {@link User#createUsernameAndPassword(String, String, FileHandler)} (statisk)</li>
     *   <li>Ved succes: vis besked og hop til login-flow</li>
     * </ol>
     *
     * @return {@code true} hvis registrering + efterfølgende login lykkes, ellers {@code false}
     */
    private boolean doRegister() {
        String usernameInput = textUI.promptText("Opret brugernavn");
        String passwordInput = textUI.promptText("Opret adgangskode");
        boolean b = User.createUsernameAndPassword(usernameInput, passwordInput, fh);
        if (b) {
            textUI.displayMsg("Register succesfuld!");
            return showLoginMenu();
        } else textUI.displayMsg("Noget gik galt med registrering.");
        return false;
    }

    public User createUserAndPortfolio(FileHandler fh, String username, String password) {
        return new User(fh, username, password);
    }

    private void showMarket() {
        textUI.displayMsg("===AktieMarked===");
        int i = 1;
        for (Stock stock : market.getStocks()) {
            textUI.displayMsg(i + ". " + stock.toString());
            i++;
        }
    }

    private void initialzeMarket() {
        market.addStock(new Stock("Apple", StockType.TECH, 150));
        market.addStock(new Stock("Google", StockType.TECH, 140));
        market.addStock(new Stock("Shell", StockType.ENERGY, 75));
        market.addStock(new Stock("Pfizer", StockType.HEALTH, 90));
    }

    private void showPortfolio() {
        textUI.displayMsg("===Portfolio===");
        for (StockHolding h : user.getPortfolio().getHoldings()) {
            textUI.displayMsg(h.toString());
        }
    }

    private void buyStock() {
        showMarket();
        textUI.displayMsg("Vælg aktie at købe");
        int choice = textUI.promptNumeric("Vælg aktie at købe (1-" + market.getStocks().size() + ")");
        if (choice < 1 || choice > market.getStocks().size()) {
            textUI.displayMsg("Ugyldigt valg");
            return;
        }
        Stock selectedStock = market.getStocks().get(choice - 1);
        int amount = textUI.promptNumeric("Vælg mængde at købe");
        boolean success = user.buyStock(selectedStock, amount, selectedStock.getPrice());
        if  (!success) {
            textUI.displayMsg("Du har ikke nok penge til at købe dette","", "Balance: "+ user.getBalance(),"", "Totale pris: " + selectedStock.getPrice()*amount, "");
        }
    }


    private void sellStock() {
        showPortfolio();
        int choice = textUI.promptNumeric("Vælg aktie at sælge (1-" + user.getPortfolio().getHoldings().size() + ")");
        if (choice < 1 || choice > user.getPortfolio().getHoldings().size()) {
            textUI.displayMsg("Ugyldigt valg");
            return;
        }
        Stock selectedStock = market.getStocks().get(choice - 1);
        int amount = textUI.promptNumeric("Vælg mængde at sælge");
        user.sellStock(selectedStock, amount, selectedStock.getPrice());


    }

    private void nextDay() {
        algorithm.doNextDay(market);
    }

    private void logout() {
       user.saveUser();
       System.exit(0);
    }
}
