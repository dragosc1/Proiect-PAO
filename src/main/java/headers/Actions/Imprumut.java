package headers.Actions;

import headers.Publicatie.Publicatie;
import headers.Utilizator.Utilizator;

import java.util.Date;
import java.util.Objects;

// Clasa Imprumut
public class Imprumut {
    private Utilizator utilizator;
    private Publicatie publicatie;
    private Date dataImprumut;
    private Date dataReturnare;

    public Imprumut(Utilizator utilizator, Publicatie publicatie, Date dataImprumut, Date dataReturnare) {
        this.utilizator = utilizator;
        this.publicatie = publicatie;
        this.dataImprumut = dataImprumut;
        this.dataReturnare = dataReturnare;
    }

    // Getters si setters
    public Utilizator getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
    }

    public Publicatie getPublicatie() {
        return publicatie;
    }

    public void setPublicatie(Publicatie publicatie) {
        this.publicatie = publicatie;
    }

    public Date getDataImprumut() {
        return dataImprumut;
    }

    public void setDataImprumut(Date dataImprumut) {
        this.dataImprumut = dataImprumut;
    }

    public Date getDataReturnare() {
        return dataReturnare;
    }

    public void setDataReturnare(Date dataReturnare) {
        this.dataReturnare = dataReturnare;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Imprumut imprumut = (Imprumut) obj;
        return Objects.equals(utilizator, imprumut.utilizator) &&
                Objects.equals(publicatie, imprumut.publicatie) &&
                Objects.equals(dataImprumut, imprumut.dataImprumut) &&
                Objects.equals(dataReturnare, imprumut.dataReturnare);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utilizator, publicatie, dataImprumut, dataReturnare);
    }
}