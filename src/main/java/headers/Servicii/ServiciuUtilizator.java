package headers.Servicii;

import headers.Utilizator.Utilizator;

import java.util.ArrayList;
import java.util.List;

public class ServiciuUtilizator {
    private List<Utilizator> listaUtilizatori;

    public ServiciuUtilizator() {
        listaUtilizatori = new ArrayList<>();
    }

    // Metode pentru gestionarea utilizatorilor
    public void adaugaUtilizator(Utilizator utilizator) {
        listaUtilizatori.add(utilizator);
    }

    public List<Utilizator> getListaUtilizatori() {
        return listaUtilizatori;
    }
}