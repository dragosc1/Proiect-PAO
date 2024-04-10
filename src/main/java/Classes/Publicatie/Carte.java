package Classes.Publicatie;

// Clasa Carte (extinde Publicatie)
public class Carte extends Publicatie {

    public Carte(String titlu, Autor autor, Sectie sectie, int anPublicare, int numarExemplare) {
        super(titlu, autor, sectie, anPublicare, numarExemplare);
    }

    @Override
    public String toString() {
        return "Carte: " + this.titlu;
    }
}