package Classes.Services;

import Classes.Publication.Author;
import oracle.jdbc.datasource.impl.OracleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AuthorService {
    // Singleton pattern
    private static AuthorService instance;

    private Connection connection;
    public AuthorService() {

    }

    public static synchronized AuthorService getInstance() {
        if (instance == null) {
            instance = new AuthorService();
        }
        return instance;
    }

    public Author getAuthorByFirstAndLastName(String name, String firstName) {
        Author author = null;
        try {
            String sql = "SELECT * FROM author WHERE name = ? AND firstname = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, firstName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                author = new Author(id, name, firstName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return author;
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

    public void insertAuthorIfItsNot(String name, String firstName) {
        try {
            // First, check if the author already exists
            Author existingAuthor = getAuthorByFirstAndLastName(name, firstName);

            // If the author doesn't exist, insert a new one
            if (existingAuthor == null) {
                String idQuery = "SELECT MAX(id) FROM author";
                PreparedStatement idStatement = connection.prepareStatement(idQuery);
                ResultSet idResultSet = idStatement.executeQuery();
                idResultSet.next();
                int nextId = idResultSet.getInt(1) + 1;

                // Prepare SQL INSERT statement
                String sql = "INSERT INTO author (id, name, firstname) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, nextId);
                statement.setString(2, name);
                statement.setString(3, firstName);
                statement.executeUpdate();

            } else {
                System.out.println("Author already exists!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        writeToAuditService(List.of("Insert author if it is not", getCurrentTimestamp()));
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
}
