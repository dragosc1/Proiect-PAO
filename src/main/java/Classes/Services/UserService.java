package Classes.Services;

import Classes.User.User;
import oracle.jdbc.datasource.impl.OracleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserService {
    // Singleton pattern
    private static UserService instance;

    private Connection connection;

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
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

    public void displayAllLibraryUsers() {
        System.out.println("Displaying all library users:");
        GenericCRUDService<User> userCRUDService = GenericCRUDService.getInstance();
        userCRUDService.openConnection();

        for (User user : userCRUDService.retrieveAll(User.class))
            System.out.println(user);
        System.out.println();
        userCRUDService.closeConnection();

        writeToAuditService(List.of("Display all library users", getCurrentTimestamp()));
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
