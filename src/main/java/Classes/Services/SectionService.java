package Classes.Services;

import Classes.Publication.Section;
import oracle.jdbc.datasource.impl.OracleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SectionService {
    // Singleton pattern
    private static SectionService instance;

    private Connection connection;
    public SectionService() {
    }

    public static synchronized SectionService getInstance() {
        if (instance == null) {
            instance = new SectionService();
        }
        return instance;
    }

    public Section retrieveSectionByName(String name) {
        Section section = null;
        try {
            String sql = "SELECT * FROM SECTION WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("description");
                section = new Section(id, name, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return section;
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

    public void insertSectionIFItsNot(String name, String description) throws SQLException {
        Section existingSection = retrieveSectionByName(name);

        // If the section doesn't exist, insert a new one
        if (existingSection == null) {
            // Prepare SQL INSERT statement
            String sql = "INSERT INTO SECTION (id, name, description) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Get the next available ID for the section
            int nextId;
            String idQuery = "SELECT MAX(id) FROM SECTION";
            PreparedStatement idStatement = connection.prepareStatement(idQuery);
            ResultSet idResultSet = idStatement.executeQuery();
            if (idResultSet.next()) {
                nextId = idResultSet.getInt(1) + 1;
            } else {
                nextId = 1;
            }

            // Set values for the INSERT statement
            statement.setInt(1, nextId);
            statement.setString(2, name);
            statement.setString(3, description);

            // Execute the SQL query
            statement.executeUpdate();
        } else {
            System.out.println("Section already exists!");
        }
        writeToAuditService(List.of("Insert section if it is not", getCurrentTimestamp()));
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