package model;

import util.FileHandler;
import util.TextUI;

import java.nio.file.Path;
import java.util.List;

public class User {
    private final String username;
    private String password;
    private double balance;
    private final Portfolio portfolio;
    private Trade trades;
    private Path balanceFilePath;
    private Path portfolioFilePath;

    /**
     * Filhåndteringsobjekt der anvendes til at gemme og læse filer.
     */
    private final FileHandler fh;

    /**
     * Fælles tekst-UI til udskrivning af beskeder.
     */
    private static final TextUI ui = new TextUI();

    /**
     * Opretter et nyt {@link User}-objekt med tilknyttet {@link FileHandler}.
     *
     * @param fh       den {@link FileHandler} der skal bruges til filadgang
     * @param username brugerens brugernavn
     * @param password brugerens adgangskode
     */
    public User(FileHandler fh, String username, String password) {
        this.username = username;
        this.password = password;
        this.fh = fh;
        this.portfolio = new Portfolio();
    }

    /**
     * Validerer et loginforsøg ud fra brugernavn og adgangskode.
     * <p>
     * Matcher på linjer i {@code users.txt}, hvor kolonne 0 = brugernavn
     * og kolonne 1 = password.
     *
     * @param username brugernavnet der skal tjekkes
     * @param password adgangskoden der skal tjekkes
     * @param fh       {@link FileHandler} der anvendes til filopslag
     * @return {@code true} hvis kombinationen findes i filen, ellers {@code false}
     */
    public static boolean authenticateUser(String username, String password, FileHandler fh) {

        Path filePath = fh.getFile("data/users.txt");

        return fh.checkMatchFile(filePath, 0, username, 1, password, 2);
    }

    public void createUserFiles() {
        balanceFilePath = Path.of("data", "userdata", getUsername(), "balance.txt");
        portfolioFilePath = Path.of("data", "userdata", getUsername(), "portfolio.txt");
        fh.createFileAndPath(balanceFilePath);
        fh.createFileAndPath(portfolioFilePath);
    }

    /**
     * Opretter en ny bruger i brugerfilen.
     * <p>
     * Tilføjer linjen {@code "brugernavn;password"} i
     * <pre>Untiteled Stock Game/data/users.txt</pre>.
     *
     * @param username brugernavn der skal oprettes
     * @param password adgangskode
     * @param fh       filhåndtering der bruges til at skrive
     * @return {@code true} hvis brugeren blev gemt korrekt, ellers {@code false}
     */
    public static boolean createUsernameAndPassword(String username, String password, FileHandler fh) {

        Path filePath = fh.getFile("data/", "users.txt");

        String usernameAndPassword = username + ";" + password;
        if (!fh.stringFileWriterAppend(filePath, usernameAndPassword)) {
            ui.displayMsg("Noget gik galt ved oprettelse af bruger");
            return false;
        }
        return true;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public boolean buyStock(Stock stock, int amount, double price) {
        if (amount * price < balance) {
            portfolio.addHolding(stock, amount, price);
            balance -= amount*price;
            return true;
        }
        return false;

    }

    public void sellStock(Stock stock, int amount, double price) {
        portfolio.removeHolding(stock, amount);
        addOrRemoveBalance(amount * price);
    }

    public double getBalance() {
        return balance;
    }

    public void addOrRemoveBalance(double amount) {
        this.balance += amount;
    }

    public String getUsername() {
        return username;
    }
    public void initializeBalance() {

       List<String> balanceList = fh.returnFile(Path.of("data", "userdata", username, "balance.txt"));
       if (balanceList.isEmpty()) {
           balanceList.add("2000");
       }
       balance = Double.parseDouble(balanceList.getFirst());

    }
    public void saveUser() {
        savePortfolio();
        saveBalance();
    }
    private void savePortfolio() {

    }
    private void saveBalance() {
        fh.stringFileWriter(balanceFilePath, String.valueOf(getBalance()));
    }
}
