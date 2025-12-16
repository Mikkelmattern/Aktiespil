package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * FileHandler-klassen står for al håndtering af filer i programmet.
 * Den kan oprette, læse, skrive og tjekke filer samt oprette nødvendige mapper.
 * Klassen bruges bl.a. til at gemme og hente brugere, film og andre data fra .txt-filer.
 */

public class FileHandler {
    // Writer bruges til at skrive til filer
    FileWriter writer;

    // Generisk Path-objekt, bruges til midlertidig reference af filstier
    Path path;

    //Tekstbrugerflade til udskrivning af fejl- og statusbeskeder
    TextUI ui = new TextUI();
    // File-objekt, bruges i forbindelse med findOrCreateAndWriteFile-metoden
    File file;

    /**
     * Opretter et {@link Path} objekt ud fra et eller flere stinavne.
     * Bruges til at samle delstier til en fuld sti under projektets rodmappe.
     *
     * @param fileName Ét eller flere elementer i stien (fx "data", "users.txt")
     * @return En {@link Path} repræsentation af den samlede sti
     */
    public Path getFile(String... fileName) {
        String baseDirectory = System.getProperty("user.dir");
        return Paths.get(baseDirectory, fileName);
    }
    /**
     * Skriver én eller flere linjer tekst til en fil.
     * Hvis filen ikke findes, antages det at den allerede er oprettet.
     * Hver linje afsluttes med systemets line separator.
     *
     * @param filePath   Stien til filen
     * @param writeInput En eller flere tekstlinjer, der skal skrives
     * @return {@code true} hvis der blev skrevet uden fejl, ellers {@code false}
     */
    public boolean stringFileWriter(Path filePath, String... writeInput) {

        try {
            writer = new FileWriter(filePath.toFile(), true);
            for (String s : writeInput) {
                writer.write(s + System.lineSeparator());
            }
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    /**
     * Sørger for at mappen og filen eksisterer.
     * Hvis mappen ikke findes, bliver den oprettet.
     * Hvis filen ikke findes, bliver den oprettet.
     *
     * @param filePath Stien til filen der skal sikres eksisterer
     */
    public void createFileAndPath(Path filePath){
        try  {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }
            if(!Files.exists(filePath)){
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Læser alle linjer i en fil og returnerer dem som en liste.
     * Hvis filen ikke findes, returneres en tom liste.
     *
     * @param filePath Stien til filen der skal læses
     * @return En {@link List} af tekstlinjer, eller en tom liste hvis filen ikke findes
     */
    public List<String> returnFile(Path filePath) {
        try {
            if (true == Files.notExists(filePath)) {
                return new ArrayList<>();
            } else {
                return Files.readAllLines(filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * Tjekker om et givent element findes i en tekstfil.
     * Linjer splittes med ';' og der søges efter et bestemt element på en given position i split-arrayet.
     *
     * @param filePath            Stien til filen
     * @param checkPlace          Den tekst der skal søges efter
     * @param lengthOfSplitLine   Minimumslængden på den splittede linje
     * @param checkThisArrayPlace Den position i arrayet hvor der skal søges
     * @return {@code true} hvis elementet findes, ellers {@code false}
     */
    public boolean checkFile(Path filePath, String checkPlace, int lengthOfSplitLine, int checkThisArrayPlace) {
        List<String> listOfFile;
        try {
            //Hvis de ikke eksisterer retunerer den false og skriver fejlmeddelselse
            if (!Files.exists(filePath)) {
                ui.displayMsg(filePath.toString() + " eksisterer ikke");
                ui.displayMsg("Fejl i checkFile(Slet dette)");
                return false;
            }
            listOfFile = Files.readAllLines(Path.of(filePath.toString())); //Læser alle linjer i filen

            //Splitter alle linjerne i filen og tjekker om "checkForThis" indgår i filen
            for (String s : listOfFile) {
                String[] split = s.split(";"); //Laver et String array over den splittede linje, med ; som seperator
                if (split.length >= lengthOfSplitLine && split[checkThisArrayPlace].equalsIgnoreCase(checkPlace)) { //Tjek det splittede array for checkPlace på checkThisArrayPlace's plads
                    //lengthOfSplitLine er typisk længden af linjen -1 fx: Brugernavn;Password er 1
                    return true;
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }
    /**
     * Tjekker om to værdier matcher i samme linje i en fil.
     * Fx bruges denne til at tjekke brugernavn og password på samme linje.
     *
     * @param filePath  Stien til filen
     * @param colA      Kolonne A der skal tjekkes
     * @param valA      Værdi for kolonne A
     * @param colB      Kolonne B der skal tjekkes
     * @param valB      Værdi for kolonne B
     * @param minColumn Minimum antal kolonner der skal være i linjen
     * @return {@code true} hvis begge værdier findes på samme linje, ellers {@code false}
     */
    public boolean checkMatchFile(Path filePath,
                                  int colA, String valA,
                                  int colB, String valB,
                                  int minColumn) {
        List<String> listOfFile;
        try {
            if (Files.notExists(filePath)) {
                ui.displayMsg("Filen findes ikke");
                return false;
            }
            listOfFile = Files.readAllLines(filePath); //Path.of(filePath.toString())
            for (String s : listOfFile) {
                String[] strings = s.split(";");
                if (strings.length >= minColumn) { // [0] = Mikkel [1] = 123
                    boolean a = strings[colA].equals(valA); //Tjekker kolonne A for value A
                    boolean b = strings[colB].equals(valB); //Tjekker kolonne B for value B
                    if (a && b) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }}
