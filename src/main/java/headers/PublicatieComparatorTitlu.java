package headers;

import java.util.Comparator;

public class PublicatieComparatorTitlu implements Comparator<Publicatie> {
    @Override
    public int compare(Publicatie p1, Publicatie p2) {
        return p1.getTitlu().toUpperCase().compareTo(p2.getTitlu().toUpperCase());
    }
}
