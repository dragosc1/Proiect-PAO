package org.example;

import headers.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    private static ServiciuUtilizator serviciuUtilizator;
    private static ServiciuPublicatie serviciuPublicatie;
    private static ServiciuImprumut serviciuImprumut;

    public static void seeding() {
        Utilizator utilizator1 = new Utilizator("John Doe", "123 Main Street", "555-1234");
        Utilizator utilizator2 = new Utilizator("Jane Smith", "456 Oak Avenue", "555-5678");
        Utilizator utilizator3 = new Utilizator("Alice Johnson", "789 Elm Street", "555-9876");
        Utilizator utilizator4 = new Utilizator("Michael Brown", "567 Pine Street", "555-5432");
        Utilizator utilizator5 = new Utilizator("Emily Davis", "890 Maple Street", "555-7890");

        serviciuUtilizator = new ServiciuUtilizator();
        serviciuUtilizator.adaugaUtilizator(utilizator1);
        serviciuUtilizator.adaugaUtilizator(utilizator2);
        serviciuUtilizator.adaugaUtilizator(utilizator3);
        serviciuUtilizator.adaugaUtilizator(utilizator4);
        serviciuUtilizator.adaugaUtilizator(utilizator5);

        // Adăugarea autorilor
        Autor autor1 = new Autor("Stephen", "King");
        Autor autor2 = new Autor("J.K.", "Rowling");
        Autor autor3 = new Autor("Agatha", "Christie");
        Autor autor4 = new Autor("Dan", "Brown");
        Autor autor5 = new Autor("Haruki", "Murakami");

        // Adăugarea sectiilor
        Sectie sectie1 = new Sectie("Fiction");
        Sectie sectie2 = new Sectie("Fantasy");
        Sectie sectie3 = new Sectie("Mystery");
        Sectie sectie4 = new Sectie("Science Fiction");
        Sectie sectie5 = new Sectie("Romance");

        // Adăugarea cărților
        Carte carte1 = new Carte("The Shining", autor1, sectie1, 1977, 2);
        Carte carte2 = new Carte("Harry Potter and the Philosopher's Stone", autor2, sectie2, 1997, 1);
        Carte carte3 = new Carte("Murder on the Orient Express", autor3, sectie3, 1934, 1);
        Carte carte4 = new Carte("The Da Vinci Code", autor4, sectie4, 2003, 3);
        Carte carte5 = new Carte("Norwegian Wood", autor5, sectie5, 1987, 4);
        Carte carte6 = new Carte("To Kill a Mockingbird", autor1, sectie1, 1960, 5);
        Carte carte7 = new Carte("The Hobbit", autor2, sectie2, 1937, 0);
        Carte carte8 = new Carte("The Catcher in the Rye", autor3, sectie1, 1951, 0);
        Carte carte9 = new Carte("1984", autor4, sectie4, 1949, 5);
        Carte carte10 = new Carte("Pride and Prejudice", autor5, sectie5, 1813, 3);

        // Adăugarea revistelor
        Revista revista1 = new Revista("National Geographic", autor2, sectie1, 2022, 1);
        Revista revista2 = new Revista("Time", autor3, sectie2, 2023, 2);
        Revista revista3 = new Revista("Scientific American", autor4, sectie4, 2021, 3);
        Revista revista4 = new Revista("Vogue", autor5, sectie5, 2024, 5);
        Revista revista5 = new Revista("The New Yorker", autor1, sectie3, 2020, 1);
        Revista revista6 = new Revista("Wired", autor2, sectie4, 2019, 0);
        Revista revista7 = new Revista("Sports Illustrated", autor3, sectie5, 2018, 1);
        Revista revista8 = new Revista("Forbes", autor4, sectie1, 2017, 0);
        Revista revista9 = new Revista("Entertainment Weekly", autor5, sectie2, 2016, 2);
        Revista revista10 = new Revista("Nature", autor1, sectie3, 2015, 3);

        // Adăugarea în serviciul de publicații
        serviciuPublicatie = new ServiciuPublicatie();
        serviciuPublicatie.adaugaPublicatie(carte1);
        serviciuPublicatie.adaugaPublicatie(carte2);
        serviciuPublicatie.adaugaPublicatie(carte3);
        serviciuPublicatie.adaugaPublicatie(carte4);
        serviciuPublicatie.adaugaPublicatie(carte5);
        serviciuPublicatie.adaugaPublicatie(carte6);
        serviciuPublicatie.adaugaPublicatie(carte7);
        serviciuPublicatie.adaugaPublicatie(carte8);
        serviciuPublicatie.adaugaPublicatie(carte9);
        serviciuPublicatie.adaugaPublicatie(carte10);

        serviciuPublicatie.adaugaPublicatie(revista1);
        serviciuPublicatie.adaugaPublicatie(revista2);
        serviciuPublicatie.adaugaPublicatie(revista3);
        serviciuPublicatie.adaugaPublicatie(revista4);
        serviciuPublicatie.adaugaPublicatie(revista5);
        serviciuPublicatie.adaugaPublicatie(revista6);
        serviciuPublicatie.adaugaPublicatie(revista7);
        serviciuPublicatie.adaugaPublicatie(revista8);
        serviciuPublicatie.adaugaPublicatie(revista9);
        serviciuPublicatie.adaugaPublicatie(revista10);


        // Imprumuturi
        Imprumut imprumut1 = new Imprumut(utilizator1, carte1, new Date(), null);
        Imprumut imprumut2 = new Imprumut(utilizator2, revista1, new Date(), null);
        Imprumut imprumut3 = new Imprumut(utilizator3, carte2, new Date(), null);
        Imprumut imprumut4 = new Imprumut(utilizator4, carte3, new Date(), null);
        Imprumut imprumut5 = new Imprumut(utilizator5, carte4, new Date(), null);

        serviciuImprumut = new ServiciuImprumut();
        serviciuImprumut.adaugaImprumut(imprumut1);
        serviciuImprumut.adaugaImprumut(imprumut2);
        serviciuImprumut.adaugaImprumut(imprumut3);
        serviciuImprumut.adaugaImprumut(imprumut4);
        serviciuImprumut.adaugaImprumut(imprumut5);
    }
    public static void main(String[] args) {
        // Introducere date
        seeding();

        // 1. Cautarea tuturor publicatiilor dintr-o anumita sectie
        System.out.println("Cautare publicatii din sectiunea \"Fiction\":");
        for (Publicatie publicatie : serviciuPublicatie.cautaPublicatiiDinSectie(new Sectie("Fiction"))) {
            System.out.println(publicatie.getTitlu());
        }
        System.out.println();

        // 2. Afisarea tuturor utilizatorilor bibliotecii
        System.out.println("Afisarea tuturor utilizatorilor bibliotecii:");
        for (Utilizator utilizator : serviciuUtilizator.getListaUtilizatori()) {
            System.out.println("Nume utilizator: " + utilizator.getNume());
            System.out.println("Adresa: " + utilizator.getAdresa());
            System.out.println("Numar de telefon: " + utilizator.getNumarTelefon());
            System.out.println();
        }

        // 3. Sa se afiseze cartile / revistele in ordinea crescatoare a titlului
        System.out.println("Cartile / Revistele in ordinea crescatoare a titlului\n");
        System.out.println("----- Carti -----");
        for (Carte carte : serviciuPublicatie.getCartiSortateDupaTitlu()) {
            System.out.println(carte);
        }
        System.out.println();
        System.out.println("----- Reviste -----");
        for (Revista revista : serviciuPublicatie.getRevisteSortateDupaTitlu()) {
            System.out.println(revista);
        }

        // 4. Sa se afiseze publicațiile disponibile pentru împrumut
        System.out.println("\nPublicatiile disponibile pentru imprumut:");
        for (Publicatie publicatie : serviciuPublicatie.getPublicatiiDisponibileImprumut(serviciuImprumut))
            System.out.println(publicatie.getTitlu());

        // 5. Sa se afiseze publicatiile unui autor
        System.out.println("\nPublicatiile lui Stephen King sunt:\n");
        Autor autorCautat = new Autor("Stephen", "King");
        List<Publicatie> publicatiiAutor = serviciuPublicatie.cautaPublicatiiDupaAutor(autorCautat);
        for (Publicatie publicatie : publicatiiAutor) {
            System.out.println(publicatie.getTitlu());
        }

    }
}