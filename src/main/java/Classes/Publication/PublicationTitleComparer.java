package Classes.Publication;

import java.util.Comparator;

public class PublicationTitleComparer implements Comparator<Publication> {
    @Override
    public int compare(Publication p1, Publication p2) {
        return p1.getTitle().toUpperCase().compareTo(p2.getTitle().toUpperCase());
    }
}