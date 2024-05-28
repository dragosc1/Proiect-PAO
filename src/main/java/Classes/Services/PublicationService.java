package Classes.Services;

import Classes.Publication.*;
import Classes.Actions.Loan;
import oracle.jdbc.datasource.impl.OracleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PublicationService {
    // Singleton pattern
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


    public void searchPublicationsByAuthor(String name, String firstName) {
        System.out.println("\nPublications made by King Stephen are:\n");

        AuthorService authorService = AuthorService.getInstance();
        authorService.openConnection();
        Author searchedAuthor = authorService.getAuthorByFirstAndLastName(name, firstName);
        authorService.closeConnection();

        GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
        publicationService.openConnection();
        for (Publication publication : publicationService.retrieveAll(Publication.class)) {
            if (searchedAuthor.equals(publication.getAuthor())) {
                System.out.println(publication);
            }
        }
        publicationService.closeConnection();
        writeToAuditService(List.of("Search publications by author ", getCurrentTimestamp()));
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

    public void displayPublicationsSortedByAuthorName() {
        System.out.println("Publications sorted by author name:");
        for (Publication publication : getPublicationsSortedByAuthorName()) {
            System.out.println(publication);
        }
        writeToAuditService(List.of("Display publications sorted by author name", getCurrentTimestamp()));
    }

    public void displayMagazinesSortedByNumberOfCopies() {
        System.out.println("Magazines sorted by number of copies:");
        for (Magazine magazine : getMagazinesSortedByNumberOfCopies()) {
            System.out.println(magazine);
        }
        System.out.println();
        writeToAuditService(List.of("Display magazines sorted by number of copies", getCurrentTimestamp()));
    }

    public void displayBooksSortedByPublicationYear() {
        System.out.println("Books sorted by publication year:");
        for (Book sortedBook : getBooksSortedByYear()) {
            System.out.println(sortedBook);
        }
        System.out.println();
        writeToAuditService(List.of("Display books sorted by publication year", getCurrentTimestamp()));
    }

    public void DisplayAllTypesOfPublicationsOrderedByTitle() {
        System.out.println("Books / Magazines / Newspapers in ascending order of title\n");
        System.out.println("----- Books -----");
        for (Book book : getBooksSortedByTitle()) {
            System.out.println(book);
        }
        System.out.println();
        System.out.println("----- Magazines -----");
        for (Magazine magazine : getMagazinesSortedByTitle()) {
            System.out.println(magazine);
        }
        System.out.println();
        System.out.println("----- Newspapers -----");
        for (Newspaper newspaper : getNewspapersSortedByTitle()) {
            System.out.println(newspaper);
        }
        writeToAuditService(List.of("Display all types of publications ordered by title", getCurrentTimestamp()));
    }

    public void displayAllPublicationsWhichCanBeLoaned() {
        System.out.println("\nPublications available for loan:");

        for (Publication publication : getPublicationsAvailableForLoan())
            System.out.println(publication.getTitle());
        System.out.println();
        writeToAuditService(List.of("Display all publications which can be loaned", getCurrentTimestamp()));
    }

    public void searchPublicationsFromASection(String section) {
        System.out.println();
        System.out.println("Searching for publications in the " + section + " section:");

        SectionService sectionService = SectionService.getInstance();

        sectionService.openConnection();
        Section section_to_find = sectionService.retrieveSectionByName(section);
        sectionService.closeConnection();

        PublicationService publicationService = PublicationService.getInstance();
        publicationService.openConnection();
        for (Publication publication : publicationService.searchPublicationsBySection(section_to_find)) {
            System.out.println(publication);
        }
        System.out.println();
        writeToAuditService(List.of("Search publication from section " + section, getCurrentTimestamp()));
    }

    private void writeToAuditService(List<String> data) {
        AuditService auditService = AuditService.getInstance();
        auditService.writeToOperationsCSV(data);
    }

    private static String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    public void deleteNewspapersFromYear(int year) {
        try {
            String sql = "DELETE FROM Newspaper WHERE EXTRACT(YEAR FROM publication_date) = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, year);
            statement.executeUpdate();
            writeToAuditService(List.of("Delete newspapers from year: " + year, getCurrentTimestamp()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewPublication(Publication publication) {
        GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
        publicationService.openConnection();
        publicationService.insert(publication);
        publicationService.closeConnection();
        writeToAuditService(List.of("Added new publication", getCurrentTimestamp()));
    }

    public int getNextId() {
        int nextId = 0;
        try {
            String sql = "SELECT MAX(id) AS max_id FROM Publication";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nextId = resultSet.getInt("max_id") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nextId;
    }

    public void addNewBook(Book book) {
        GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
        publicationService.openConnection();
        Publication publication = new Publication(book.getId(), book.getTitle(), book.getAuthor(), book.getSection(), book.getPublicationYear(), book.getNumberOfCopies());
        publicationService.insert(publication);
        publicationService.closeConnection();
        GenericCRUDService<Book> bookService = GenericCRUDService.getInstance();
        bookService.openConnection();
        bookService.insert(book);
        bookService.closeConnection();
        writeToAuditService(List.of("Added new book", getCurrentTimestamp()));
    }

    public void addNewMagazine(Magazine magazine) {
        GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
        publicationService.openConnection();
        Publication publication = new Publication(magazine.getId(), magazine.getTitle(), magazine.getAuthor(), magazine.getSection(), magazine.getPublicationYear(), magazine.getNumberOfCopies());
        publicationService.insert(publication);
        publicationService.closeConnection();
        GenericCRUDService<Magazine> magazineService = GenericCRUDService.getInstance();
        magazineService.openConnection();
        magazineService.insert(magazine);
        magazineService.closeConnection();
        writeToAuditService(List.of("Added new Magazine", getCurrentTimestamp()));
    }

    public void addNewNewspaper(Newspaper newspaper) {
        GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
        publicationService.openConnection();
        Publication publication = new Publication(newspaper.getId(), newspaper.getTitle(), newspaper.getAuthor(), newspaper.getSection(), newspaper.getPublicationYear(), newspaper.getNumberOfCopies());
        publicationService.insert(publication);
        publicationService.closeConnection();
        GenericCRUDService<Newspaper> newspaperService = GenericCRUDService.getInstance();
        newspaperService.openConnection();
        newspaperService.insert(newspaper);
        newspaperService.closeConnection();
        writeToAuditService(List.of("Added new Newspaper", getCurrentTimestamp()));
    }
}
