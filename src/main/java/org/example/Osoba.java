package org.example;

@Opis(wersja = "2")
@TestKlasy(wartosci_pol = {"Abc","2000"})
public class Osoba {

    String nazwisko;
    int rok_urodzenia;

    public Osoba(String nazwisko, int rok_urodzenia) {
        this.nazwisko = nazwisko;
        this.rok_urodzenia = rok_urodzenia;
    }

    @Opis(wersja = "1.5")
    @TestMetody(spodziewany_wynik = "23", argumenty = {"2023"})
    int wiek(String rok) {
        return Integer.parseInt(rok)-rok_urodzenia;
    }

    @Opis(wersja = "1.5")
    @TestMetody(spodziewany_wynik = "20", argumenty = {"2020"})
    int wiek(int rok) {
        return rok-rok_urodzenia;
    }

    @TestMetody(spodziewany_wynik = "Abc", argumenty = {})
    String nazwisko() {
        return nazwisko;
    }
}
