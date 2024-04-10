package headers;

import java.util.Objects;

public class Publicatie {
    protected String titlu;
    protected Autor autor;
    protected Sectie sectie;
    protected int anPublicare;
    protected int numarExemplare;

    public Publicatie(String titlu, Autor autor, Sectie sectie, int anPublicare, int numarExemplare) {
        this.titlu = titlu;
        this.autor = autor;
        this.sectie = sectie;
        this.anPublicare = anPublicare;
        this.numarExemplare = numarExemplare;
    }

    // Getters si setters pentru atributele comune
    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Sectie getSectie() {
        return sectie;
    }

    public void setSectie(Sectie sectie) {
        this.sectie = sectie;
    }

    public int getAnPublicare() {
        return anPublicare;
    }

    public void setAnPublicare(int anPublicare) {
        this.anPublicare = anPublicare;
    }

    public int getNumarExemplare() {
        return numarExemplare;
    }

    public void setNumarExemplare(int numarExemplare) {
        this.numarExemplare = numarExemplare;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Publicatie publicatie = (Publicatie) obj;
        return anPublicare == publicatie.anPublicare &&
                numarExemplare == publicatie.numarExemplare &&
                Objects.equals(titlu, publicatie.titlu) &&
                Objects.equals(autor, publicatie.autor) &&
                Objects.equals(sectie, publicatie.sectie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titlu, autor, sectie, anPublicare, numarExemplare);
    }
}