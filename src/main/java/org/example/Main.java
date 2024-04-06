package org.example;

import headers.*;

import java.util.Date;

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

        // Autori
        Autor autor1 = new Autor("Stephen", "King");
        Autor autor2 = new Autor("J.K.", "Rowling");
        Autor autor3 = new Autor("Agatha", "Christie");
        Autor autor4 = new Autor("Dan", "Brown");
        Autor autor5 = new Autor("Haruki", "Murakami");

        // Sectii
        Sectie sectie1 = new Sectie("Fiction");
        Sectie sectie2 = new Sectie("Fantasy");
        Sectie sectie3 = new Sectie("Mystery");
        Sectie sectie4 = new Sectie("Science Fiction");
        Sectie sectie5 = new Sectie("Romance");

        // Publicatii
        Carte carte1 = new Carte("The Shining", autor1, sectie1, 1977);
        Carte carte2 = new Carte("Harry Potter and the Philosopher's Stone", autor2, sectie2, 1997);
        Revista revista1 = new Revista("National Geographic", autor2, sectie1, 2022);
        Revista revista2 = new Revista("Reader's Digest", autor3, sectie3, 2005);
        Carte carte3 = new Carte("The Da Vinci Code", autor4, sectie4, 2003);
        Carte carte4 = new Carte("Norwegian Wood", autor5, sectie1, 1987);

        serviciuPublicatie = new ServiciuPublicatie();
        serviciuPublicatie.adaugaPublicatie(carte1);
        serviciuPublicatie.adaugaPublicatie(carte2);
        serviciuPublicatie.adaugaPublicatie(revista1);
        serviciuPublicatie.adaugaPublicatie(revista2);
        serviciuPublicatie.adaugaPublicatie(carte3);
        serviciuPublicatie.adaugaPublicatie(carte4);

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

    }
}