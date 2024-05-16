package Classes.Services;

import Classes.Publication.Section;
import oracle.jdbc.datasource.impl.OracleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SectionService {
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

}