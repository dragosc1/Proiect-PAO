package Classes.Services;

import Classes.Publication.Author;
import Classes.Actions.Loan;
import Classes.Publication.Book;
import Classes.Publication.Publication;
import Classes.Publication.Magazine;
import Classes.Publication.PublicationTitleComparer;
import Classes.Publication.Section;

import java.util.*;

public class PublicationService {
    private List<Publication> publicationList;

    public PublicationService() {
        publicationList = new ArrayList<>();
    }


    // Methods for managing publications
    public void addPublication(Publication publication) {
        publicationList.add(publication);
    }

    public List<Publication> getPublicationList() {
        return publicationList;
    }


    // Method for searching publications in a specific section
    public List<Publication> searchPublicationsBySection(Section section) {
        List<Publication> foundPublications = new ArrayList<>();
        for (Publication publication : publicationList) {
            if (publication.getSection().equals(section)) {
                foundPublications.add(publication);
            }
        }
        return foundPublications;
    }

    // Method to get all books
    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        for (Publication publication : publicationList) {
            if (publication instanceof Book) {
                books.add((Book) publication);
            }
        }
        return books;
    }

    // Method to get all magazines
    public List<Magazine> getMagazines() {
        List<Magazine> magazines = new ArrayList<>();
        for (Publication publication : publicationList) {
            if (publication instanceof Magazine) {
                magazines.add((Magazine) publication);
            }
        }
        return magazines;
    }

    // Method to get all books sorted by title
    public Set<Book> getBooksSortedByTitle() {
        Set<Book> books = new TreeSet<>(new PublicationTitleComparer());
        for (Publication publication : publicationList) {
            if (publication instanceof Book) {
                books.add((Book) publication);
            }
        }
        return books;
    }

    // Method to get all magazines sorted by title
    public Set<Magazine> getMagazinesSortedByTitle() {
        Set<Magazine> magazines = new TreeSet<>(new PublicationTitleComparer());
        for (Publication publication : publicationList) {
            if (publication instanceof Magazine) {
                magazines.add((Magazine) publication);
            }
        }
        return magazines;
    }

    public List<Publication> getPublicationsAvailableForLoan(LoanService loanService) {
        List<Publication> result = new ArrayList<>();
        for (Publication publication : publicationList) {
            boolean available = true;
            // Check if there is an active loan for this publication
            for (Loan loan : loanService.getLoanList()) {
                if (loan.getPublication().equals(publication) && loan.getReturnDate() == null) {
                    available = false;
                    break;
                }
            }
            // Check if there are available copies for loan
            if (available && publication.getNumberOfCopies() > 0) {
                result.add(publication);
            }
        }
        return result;
    }

    public List<Publication> searchPublicationsByAuthor(Author searchedAuthor) {
        List<Publication> publicationsByAuthor = new ArrayList<>();
        for (Publication publication : publicationList) {
            if (publication.getAuthor().equals(searchedAuthor)) {
                publicationsByAuthor.add(publication);
            }
        }
        return publicationsByAuthor;
    }

    public List<Book> getBooksSortedByYear() {
        List<Book> books = getBooks();
        Collections.sort(books, Comparator.comparingInt(Book::getPublicationYear));
        return books;
    }

    public List<Magazine> getMagazinesSortedByNumberOfCopies() {
        List<Magazine> magazines = getMagazines();
        Collections.sort(magazines, Comparator.comparingInt(Magazine::getNumberOfCopies));
        return magazines;
    }

    public List<Publication> getPublicationsSortedByAuthorName() {
        List<Publication> publications = publicationList;
        Collections.sort(publications, Comparator.comparing(pub -> pub.getAuthor().getName()));
        return publications;
    }
}
