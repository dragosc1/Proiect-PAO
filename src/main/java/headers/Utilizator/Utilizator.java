package headers.Utilizator;

import java.util.Objects;

public class Utilizator {
    private String nume;
    private String adresa;
    private String numarTelefon;

    public Utilizator(String nume, String adresa, String numarTelefon) {
        this.nume = nume;
        this.adresa = adresa;
        this.numarTelefon = numarTelefon;
    }

    // Getters si setters
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Utilizator utilizator = (Utilizator) obj;
        return Objects.equals(nume, utilizator.nume) &&
                Objects.equals(adresa, utilizator.adresa) &&
                Objects.equals(numarTelefon, utilizator.numarTelefon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, adresa, numarTelefon);
    }
}