package Classes.Publication;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

// Section class
public class Section {
    private int id;
    private String name;
    private String description;

    public Section(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getter and setter for the section description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Section section = (Section) other;
        return Objects.equals(id, section.id) &&
                Objects.equals(name, section.name) &&
                Objects.equals(description, section.description); // Include description in equality check
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description); // Include description in hash calculation
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static Section parseResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description"); // Retrieve description from result set

        return new Section(id, name, description); // Pass description to constructor
    }

}