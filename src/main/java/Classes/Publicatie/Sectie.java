package Classes.Publicatie;

import java.util.Objects;

// Clasa Sectie
public class Sectie {
    private String nume;

    public Sectie(String nume) {
        this.nume = nume;
    }

    // Getter si setter pentru numele sectiei
    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Sectie sectie = (Sectie) other;
        return Objects.equals(nume, sectie.nume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume);
    }
}