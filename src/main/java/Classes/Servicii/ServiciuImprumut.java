package Classes.Servicii;

import Classes.Actions.Imprumut;
import Classes.Publicatie.Publicatie;
import Classes.Utilizator.Utilizator;

import java.util.*;

public class ServiciuImprumut {
    private Set<Imprumut> listaImprumuturi;

    public ServiciuImprumut() {
        listaImprumuturi = new TreeSet<>(Comparator.comparing(Imprumut::getDataImprumut));
    }

    // Metode pentru gestionarea imprumuturilor
    public void adaugaImprumut(Imprumut imprumut) {
        for (Imprumut existingImprumut : listaImprumuturi) {
            if (existingImprumut.getPublicatie().equals(imprumut.getPublicatie())
                    && existingImprumut.getUtilizator().equals(imprumut.getUtilizator()) && existingImprumut.getDataReturnare() == null) {
                System.out.println("Imprumutul pentru aceeași publicație și utilizator deja există!");
                return;
            }
        }

        if (imprumut.getDataReturnare() == null || imprumut.getDataReturnare().after(new Date())) {
            if (imprumut.getPublicatie().getNumarExemplare() > 0) {
                imprumut.getPublicatie().setNumarExemplare(imprumut.getPublicatie().getNumarExemplare() - 1);
                listaImprumuturi.add(imprumut);
            } else {
                System.out.println("Nu mai sunt exemplare disponibile pentru împrumut!");
            }
        } else {
            System.out.println("Data returnare trebuie să fie null sau în viitor!");
        }
    }

    public List<Publicatie> cautarePublicatiiImprumutateDeUtilizator(Utilizator utilizator) {
        List<Publicatie> publicatiiImprumutate = new ArrayList<>();

        for (Imprumut imprumut : listaImprumuturi) {
            if (imprumut.getUtilizator().equals(utilizator)) {
                publicatiiImprumutate.add(imprumut.getPublicatie());
            }
        }

        return publicatiiImprumutate;
    }

    public void returnarePublicatie(Utilizator utilizator, Publicatie publicatie) {
        for (Imprumut imprumut : listaImprumuturi) {
            if (imprumut.getUtilizator().equals(utilizator) && imprumut.getPublicatie().equals(publicatie) && imprumut.getDataReturnare() == null) {
                imprumut.setDataReturnare(new Date());
                imprumut.getPublicatie().setNumarExemplare(imprumut.getPublicatie().getNumarExemplare() + 1);
                return;
            }
        }
        System.out.println("Publicatia nu a fost imprumutata de acest utilizator.");
    }

    public Set<Imprumut> getListaImprumuturi() {
        return listaImprumuturi;
    }
}