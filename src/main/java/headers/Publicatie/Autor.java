package headers.Publicatie;

import java.util.Objects;

public class Autor {
    private String nume;
    private String prenume;

    public Autor(String nume, String prenume) {
        this.nume = nume;
        this.prenume = prenume;
    }

    // Getters si setters
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Autor other = (Autor) obj;
        return Objects.equals(nume, other.nume) &&
                Objects.equals(prenume, other.prenume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, prenume);
    }
}
