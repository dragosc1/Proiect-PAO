package Classes.Services;

import Classes.Actions.Loan;
import Classes.Publication.Publication;
import Classes.User.User;
import oracle.jdbc.datasource.impl.OracleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LoanService {
    // Singleton pattern
    private static LoanService instance;

    private Connection connection;

    public LoanService() {
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

    public static synchronized LoanService getInstance() {
        if (instance == null) {
            instance = new LoanService();
        }
        return instance;
    }

    public boolean canMakeLoan(Loan loan) {
        // Check if the user has any active loans
        GenericCRUDService<Loan> loanService = GenericCRUDService.getInstance();
        loanService.openConnection();
        List<Loan> userLoans = loanService.retrieveAll(Loan.class);

        for (Loan userLoan : userLoans) {
            if (userLoan.getUser().equals(loan.getUser()) && userLoan.getReturnDate() == null) {
                // User has an active loan
                loanService.closeConnection();
                return false;
            }
        }

        // Check if the publication is available for loaning
        Publication publication = loan.getPublication();
        boolean isPublicationAvailable = checkPublicationAvailability(publication);
        loanService.closeConnection();
        return isPublicationAvailable;
    }

    private boolean checkPublicationAvailability(Publication publication) {
        if (publication.getNumberOfCopies() > 0) {
            GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
            publicationService.openConnection();
            publication = publicationService.retrieveOneId(Publication.class, publication.getId());
            Publication new_publication = publication.createCopy();
            new_publication.setNumberOfCopies(new_publication.getNumberOfCopies() - 1);
            publicationService.update(publication, new_publication);
            publicationService.closeConnection();
            return true;
        }
        return false;
    }

    public void searchPublicationsBorrowedByUser(int Id) {
        GenericCRUDService<User> userCRUDService = GenericCRUDService.getInstance();
        userCRUDService.openConnection();
        User user = userCRUDService.retrieveOneId(User.class, Id);
        userCRUDService.closeConnection();
        List<Publication> borrowedPublications = new ArrayList<>();

        try {
            // Query loans associated with the given user
            String sql = "SELECT * FROM Loan WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();

            // Retrieve publications from loans
            while (resultSet.next()) {
                int publicationId = resultSet.getInt("publication_id");
                GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
                publicationService.openConnection();
                Publication borrowedPublication = publicationService.retrieveOneId(Publication.class, publicationId);
                if (borrowedPublication != null) {
                    borrowedPublications.add(borrowedPublication);
                }
                publicationService.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("Publications borrowed by user " + user.getName() + ":");
        for (Publication publication : borrowedPublications) {
            System.out.println(publication);
        }
        writeToAuditService(List.of("Search publications borrowed by user " + Id, getCurrentTimestamp()));
    }

    public void returnPublication(int idUser, int idPublication) {
        GenericCRUDService<User> userCRUDService = GenericCRUDService.getInstance();
        userCRUDService.openConnection();
        User user = userCRUDService.retrieveOneId(User.class, idUser);
        userCRUDService.closeConnection();

        GenericCRUDService<Publication> publicationCRUDService = GenericCRUDService.getInstance();
        publicationCRUDService.openConnection();
        Publication publication = publicationCRUDService.retrieveOneId(Publication.class, idPublication);
        publicationCRUDService.closeConnection();

        try {
            // Query the loan associated with the given user and publication
            String sql = "SELECT * FROM Loan WHERE user_id = ? AND publication_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user.getId());
            statement.setInt(2, publication.getId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Update the return date of the loan to mark it as returned
                int loanId = resultSet.getInt("id");
                sql = "UPDATE Loan SET return_date = ? WHERE id = ?";
                statement = connection.prepareStatement(sql);
                statement.setDate(1, new java.sql.Date(System.currentTimeMillis()));
                statement.setInt(2, loanId);

                Publication new_publication = publication.createCopy();
                new_publication.setNumberOfCopies(publication.getNumberOfCopies() + 1);
                GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
                publicationService.openConnection();
                publicationService.update(publication, new_publication);
                publicationService.closeConnection();

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        writeToAuditService(List.of("User " + idUser + " has return publication with id " + idPublication, getCurrentTimestamp()));
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

    public void addNewLoan(Loan loan) {
        if (!canMakeLoan(loan)) {
            System.out.println("The user cannot make a new loan. Either they have active loans or the publication is not available.");
            return;
        }

        try {
            // Insert new loan record into the Loan table
            String sql = "INSERT INTO Loan (id, user_id, publication_id, loan_date, return_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, loan.getId()); // Assuming getId() retrieves the loan's ID
            statement.setInt(2, loan.getUser().getId());
            statement.setInt(3, loan.getPublication().getId());
            statement.setDate(4, new java.sql.Date(System.currentTimeMillis())); // Convert to java.sql.Date
            statement.setNull(5, java.sql.Types.DATE); // Initially, the return_date is null

            statement.executeUpdate();

            System.out.println("Loan added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        writeToAuditService(List.of("Added new loan for user " + loan.getUser().getId() + " with publication " + loan.getPublication().getId(), getCurrentTimestamp()));
    }

    public int getNextId() {
        int nextId = 0;
        try {
            String sql = "SELECT MAX(id) AS max_id FROM Loan";
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
}
