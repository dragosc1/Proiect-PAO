package Classes.Publicatie;

// Clasa Revista (extinde Publicatie)
public class Revista extends Publicatie {

    public Revista(String titlu, Autor autor, Sectie sectie, int anPublicare, int numarExemplare) {
        super(titlu, autor, sectie, anPublicare, numarExemplare);
    }

    @Override
    public String toString() {
        return "Revista: " + this.titlu;
    }
}