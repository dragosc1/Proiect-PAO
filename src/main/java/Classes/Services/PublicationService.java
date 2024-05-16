package Classes.Services;

import Classes.Publication.*;
import Classes.Actions.Loan;
import oracle.jdbc.datasource.impl.OracleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PublicationService {
    private static PublicationService instance;

    private Connection connection;

    public PublicationService() {
    }

    public static synchronized PublicationService getInstance() {
        if (instance == null) {
            instance = new PublicationService();
        }
        return instance;
    }
    public List<Publication> searchPublicationsBySection(Section section) {
        List<Publication> foundPublications = new ArrayList<>();
        GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
        publicationService.openConnection();
        for (Publication publication : publicationService.retrieveAll(Publication.class)) {
            if (publication.getSection().equals(section)) {
                foundPublications.add(publication);
            }
        }
        publicationService.closeConnection();
        return foundPublications;
    }


    public Set<Book> getBooksSortedByTitle() {
        GenericCRUDService<Book> bookService = GenericCRUDService.getInstance();
        bookService.openConnection();
        List<Book> books = bookService.retrieveAll(Book.class);
        Set<Book> sortedBooks = new TreeSet<>(new PublicationTitleComparer());
        sortedBooks.addAll(books);
        bookService.closeConnection();
        return sortedBooks;
    }

    public Set<Magazine> getMagazinesSortedByTitle() {
        GenericCRUDService<Magazine> magazineService = GenericCRUDService.getInstance();
        magazineService.openConnection();
        List<Magazine> magazines = magazineService.retrieveAll(Magazine.class);
        Set<Magazine> sortedMagazines = new TreeSet<>(new PublicationTitleComparer());
        sortedMagazines.addAll(magazines);
        magazineService.closeConnection();
        return sortedMagazines;
    }

    public Set<Newspaper> getNewspapersSortedByTitle() {
        GenericCRUDService<Newspaper> newspaperService = GenericCRUDService.getInstance();
        newspaperService.openConnection();
        List<Newspaper> newspapers = newspaperService.retrieveAll(Newspaper.class);
        Set<Newspaper> sortedNewspapers = new TreeSet<>(new PublicationTitleComparer());
        sortedNewspapers.addAll(newspapers);
        newspaperService.closeConnection();
        return sortedNewspapers;
    }


    public List<Publication> searchPublicationsByAuthor(Author searchedAuthor) {
        List<Publication> foundPublications = new ArrayList<>();
        GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
        publicationService.openConnection();
        for (Publication publication : publicationService.retrieveAll(Publication.class)) {
            if (searchedAuthor.equals(publication.getAuthor())) {
                foundPublications.add(publication);
            }
        }
        publicationService.closeConnection();
        return foundPublications;
    }

    public void openConnection() {
        try {
            OracleDataSource obs = new OracleDataSource();
            obs.setURL("jdbc:oracle:thin:@localhost:1522:XE");
            obs.setUser("c##dragosc1");
            obs.setPassword(System.getenv("DB_PASSWORD"));
            connection = obs.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Book> getBooksSortedByYear() {
        List<Book> sortedBooks = new ArrayList<>();
        try {
            String sql = "SELECT b.*, p.* FROM Book b JOIN Publication p ON b.id = p.id ORDER BY p.publication_year";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Book book;
                GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
                publicationService.openConnection();
                Publication publication = publicationService.retrieveOneId(Publication.class, resultSet.getInt("id"));
                publicationService.closeConnection();
                book = new Book(resultSet.getInt("id"), publication.getTitle(), publication.getAuthor(), publication.getSection(), publication.getPublicationYear(), publication.getNumberOfCopies(), resultSet.getString("ISBN"));
                sortedBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sortedBooks;
    }

    public List<Magazine> getMagazinesSortedByNumberOfCopies() {
        List<Magazine> sortedMagazines = new ArrayList<>();
        try {
            String sql = "SELECT m.*, p.* FROM Magazine m JOIN Publication p ON m.id = p.id ORDER BY p.number_of_copies";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Magazine magazine;
                GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
                publicationService.openConnection();
                Publication publication = publicationService.retrieveOneId(Publication.class, resultSet.getInt("id"));
                publicationService.closeConnection();
                magazine = new Magazine(resultSet.getInt("id"), publication.getTitle(), publication.getAuthor(), publication.getSection(), publication.getPublicationYear(), publication.getNumberOfCopies(), resultSet.getInt("issue_number"));
                sortedMagazines.add(magazine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sortedMagazines;
    }

    public List<Publication> getPublicationsSortedByAuthorName() {
        List<Publication> sortedPublications = new ArrayList<>();
        try {
            String sql = "SELECT p.*, a.name FROM PUBLICATION p " +
                    "INNER JOIN Author a ON p.author_id = a.id " +
                    "ORDER BY a.name";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
                publicationService.openConnection();
                Publication publication = publicationService.retrieveOneId(Publication.class, resultSet.getInt("id"));
                publicationService.closeConnection();
                sortedPublications.add(publication);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sortedPublications;
    }

    public List<Publication> getPublicationsAvailableForLoan() {
        List<Publication> foundPublications = new ArrayList<>();
        GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
        publicationService.openConnection();
        for (Publication publication : publicationService.retrieveAll(Publication.class)) {
            if (publication.getNumberOfCopies() > 0) {
                foundPublications.add(publication);
            }
        }
        publicationService.closeConnection();
        return foundPublications;
    }
}
