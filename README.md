# Aktiespil

## Funktionelle Krav – Aktiespil (SP4 Projekt)

### 1. Brugerhåndtering

Brugeren skal kunne oprette en konto med brugernavn og password.

Brugernavn og password skal gemmes i en fil.

Brugeren skal kunne logge ind med eksisterende konto.

Hver bruger skal have sin egen gemte spiltilstand (økonomi, portefølje osv.).

### 2. Spiløkonomi og Aktier

Brugeren starter med et fast startbeløb (f.eks. 100.000).

Spillet indeholder en liste af aktier med:

Navn

Type/kategori

Startpris

Aktiernes type skal påvirke den måde, deres pris ændrer sig på.

### 3. Handelssystem

Brugeren skal kunne:

Se en liste over alle aktier med deres nuværende pris.

Købe aktier, hvis de har penge nok.

Sælge aktier, de ejer, og modtage korrekt beløb.

Systemet skal opdatere porteføljen korrekt efter køb og salg.

### 4. Portefølje og Historik

Brugeren skal kunne se sin portefølje, som indeholder:

Aktie­navn

Antal aktier

Gennemsnitlig købspris

Nuværende pris

Samlet værdi

Brugeren skal kunne se en liste over tidligere handler (dato, aktie, antal, pris, køb/salg).

Programmet skal kunne vise en rapport over gevinst/tab:

Realiseret gevinst/tab

Urealiseret gevinst/tab

### 5. Tid og Prisudvikling

Brugeren skal kunne lade tiden gå én dag frem.

Aktiepriserne skal opdateres efter en model, hvor typen påvirker sandsynlighed for stigning/fald.

Efter hver dag vises en nyhedsrapport med:

De største stigninger

De største fald

Hints om hvilke aktietyper der muligvis klarer sig godt eller dårligt i næste periode.

### 6. Gem og Indlæs Spil

Når brugeren logger ud, gemmes:

Pengebeholdning

Portefølje

Dagnummer

Handlerhistorik (valgfrit, men anbefalet)

Når brugeren logger ind igen, indlæses den gemte spiltilstand.

### 7. Fejlhåndtering

Programmet må ikke crashe ved ugyldigt input.

Brugeren skal få besked, hvis:

De prøver at købe aktier uden nok penge.

De prøver at sælge flere aktier end de ejer.

De vælger en aktie, der ikke findes.
