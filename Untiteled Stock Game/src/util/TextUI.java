package util;

import java.util.Scanner;
//--- Hjælpemetoder og filhåndtering ---

import java.util.ArrayList;

/**
 * En simpel tekstbaseret brugergrænseflade (util) til konsolprogrammer.
 * <p>
 * Klassen indeholder hjælpemetoder til at:
 * <ul>
 *   <li>Udskrive beskeder og lister</li>
 *   <li>Indhente numeriske valg</li>
 *   <li>Indhente binære (Ja/Nej) valg</li>
 *   <li>Indhente frit tekstinput</li>
 *   <li>Understøtte valg af flere muligheder via {@link #promptChoice(ArrayList, int, String...)}</li>
 * </ul>
 */
public class TextUI {

    private static Scanner sc = new Scanner(System.in);

    /**
     * Viser en liste af muligheder og lader brugeren vælge op til et givent antal.
     * <p>
     * Der udskrives først en valgfri besked ({@code msg}), derefter listen
     * nummereret fra 1..N. Brugeren bliver spurgt gentagne gange indtil der er
     * valgt {@code limit} antal elementer.
     *
     * @param options listen af valgmuligheder, der kan vælges imellem
     * @param limit   hvor mange valg brugeren i alt må foretage
     * @param msg     valgfri(e) besked(er), der vises før listen
     * @return en ny liste med de valgte elementer fra {@code options}
     * @throws IndexOutOfBoundsException hvis brugeren taster et tal uden for 1..options.size()
     * @implNote Metoden bruger {@link #promptNumeric(String...)} for hvert valg.
     */
    public ArrayList<String> promptChoice(ArrayList<String> options, int limit, String... msg) {
        displayMsg(msg);
        displayList(options, "");
        ArrayList<String> choices = new ArrayList<>(); //til at lave beholder til at gemme brugerens valg
        while (choices.size() < limit) {

            int choice = promptNumeric(msg);
            choices.add(options.get(choice - 1));
        }
        return choices;
    }

    /**
     * Udskriver en nummereret liste i formatet {@code "1. Tekst"} pr. linje.
     *
     * @param list listen af strenge der skal udskrives
     * @param msg  valgfri besked (aktuelt ikke anvendt i udskriften)
     */
    public void displayList(ArrayList<String> list, String msg) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + 1 + ". " + list.get(i));
        }
    }

    /**
     * Udskriver 0.. N beskeder, hver på sin egen linje.
     *
     * @param msg variadisk liste af beskeder der udskrives i rækkefølge
     */
    public void displayMsg(String... msg) {
        for (String s : msg) {
            System.out.println(s);
        }
    }

    /**
     * Beder brugeren om et numerisk input og returnerer tallet som {@code int}.
     * <p>
     * Der udskrives først de givne beskeder ({@code msg}), hvorefter der læses en
     * linje fra konsollen og forsøges konverteret via {@link Integer#parseInt(String)}.
     *
     * @param msg valgfri(e) besked(er) der vises før input
     * @return det heltal brugeren indtaster
     * @throws NumberFormatException hvis input ikke kan fortolkes som et heltal
     */
    public int promptNumeric(String... msg) {
        displayMsg(msg); //stille bruger spørgsmål
        String input = sc.nextLine(); //Giver bruger et sted at svare og vente på svar
        int numInput = Integer.parseInt(input); //skulle konvertere det indput til et tal

        return numInput;
    }

    /**
     * Beder brugeren om et binært (Ja/Nej) svar.
     * <p>
     * Accepterer {@code "Y"} for ja og {@code "N"} for nej (case-insensitive).
     * Andre svar medfører et rekursivt kald indtil der indtastes et gyldigt svar.
     *
     * @param msg valgfri(e) besked(er) der vises før input
     * @return {@code true} hvis brugeren svarer "Y", ellers {@code false} ved "N"
     */
    public boolean promptBinary(String... msg) { //Giver brugeren mulighed for ja / Nej Spørgsmål
        displayMsg(msg);
        String input = sc.nextLine();
        if (input.equalsIgnoreCase("Y")) {
            return true;
        } else if (input.equalsIgnoreCase("N")) {
            return false;
        } else {
            return promptBinary(msg);
        }
    }

    /**
     * Beder brugeren om frit tekstinput og returnerer hele linjen.
     *
     * @param msg besked der vises før input
     * @return den indtastede tekst (kan være tom streng)
     */
    public String promptText(String msg) {
        displayMsg(msg);
        return sc.nextLine();
    }

}
