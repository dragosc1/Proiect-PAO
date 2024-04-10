package headers;

import java.util.*;

public class ServiciuPublicatie {
    private List<Publicatie> listaPublicatii;

    public ServiciuPublicatie() {
        listaPublicatii = new ArrayList<>();
    }


    // Metode pentru gestionarea publicatiilor
    public void adaugaPublicatie(Publicatie publicatie) {
        listaPublicatii.add(publicatie);
    }

    public List<Publicatie> getListaPublicatii() {
        return listaPublicatii;
    }


    // Metoda pentru cautarea publicatiilor dintr-o anumita sectie
    public List<Publicatie> cautaPublicatiiDinSectie(Sectie sectie) {
        List<Publicatie> publicatiiGasite = new ArrayList<>();
        for (Publicatie publicatie : listaPublicatii) {
            if (publicatie.getSectie().equals(sectie)) {
                publicatiiGasite.add(publicatie);
            }
        }
        return publicatiiGasite;
    }

    // Metoda pentru a obtine toate cartile
    public List<Carte> getCarti() {
        List<Carte> carti = new ArrayList<>();
        for (Publicatie publicatie : listaPublicatii) {
            if (publicatie instanceof Carte) {
                carti.add((Carte) publicatie);
            }
        }
        return carti;
    }

    // Metoda pentru a obtine toate revistele
    public List<Revista> getReviste() {
        List<Revista> reviste = new ArrayList<>();
        for (Publicatie publicatie : listaPublicatii) {
            if (publicatie instanceof Revista) {
                reviste.add((Revista) publicatie);
            }
        }
        return reviste;
    }

    // Metoda pentru a obtine toate cartile sortate dupa titlu
    public Set<Carte> getCartiSortateDupaTitlu() {
        Set<Carte> carti = new TreeSet<>(new PublicatieComparatorTitlu());
        for (Publicatie publicatie : listaPublicatii) {
            if (publicatie instanceof Carte) {
                carti.add((Carte) publicatie);
            }
        }
        return carti;
    }

    // Metoda pentru a obtine toate revistele sortate dupa titlu
    public Set<Revista> getRevisteSortateDupaTitlu() {
        Set<Revista> reviste = new TreeSet<>(new PublicatieComparatorTitlu());
        for (Publicatie publicatie : listaPublicatii) {
            if (publicatie instanceof Revista) {
                reviste.add((Revista) publicatie);
            }
        }
        return reviste;
    }

    public List<Publicatie> getPublicatiiDisponibileImprumut(ServiciuImprumut serviciuImprumut) {
        List<Publicatie> rezultat = new ArrayList<>();
        for (Publicatie publicatie : listaPublicatii) {
            boolean disponibila = true;
            // Verificam daca exista un imprumut activ pentru aceasta publicatie
            for (Imprumut imprumut : serviciuImprumut.getListaImprumuturi()) {
                if (imprumut.getPublicatie().equals(publicatie) && imprumut.getDataReturnare() == null) {
                    disponibila = false;
                    break;
                }
            }
            // Verificam daca mai sunt exemplare disponibile pentru împrumut
            if (disponibila && publicatie.getNumarExemplare() > 0) {
                rezultat.add(publicatie);
            }
        }
        return rezultat;
    }

    public List<Publicatie> cautaPublicatiiDupaAutor(Autor autorCautat) {
        List<Publicatie> publicatiiAutor = new ArrayList<>();
        for (Publicatie publicatie : listaPublicatii) {
            if (publicatie.getAutor().equals(autorCautat)) {
                publicatiiAutor.add(publicatie);
            }
        }
        return publicatiiAutor;
    }

    public List<Carte> getCartiSortateDupaAn() {
        List<Carte> carti = getCarti();
                Collections.sort(carti, Comparator.comparingInt(Carte::getAnPublicare));
        return carti;
    }

    public List<Revista> getRevisteSortateDupaNumarExemplare() {
        List<Revista> reviste = getReviste();
                Collections.sort(reviste, Comparator.comparingInt(Revista::getNumarExemplare));
        return reviste;
    }

    public List<Publicatie> getPublicatiiSortateDupaAutor() {
        List<Publicatie> publicatii = listaPublicatii;
                Collections.sort(publicatii, Comparator.comparing(pub -> pub.getAutor().getNume()));
        return publicatii;
    }
}