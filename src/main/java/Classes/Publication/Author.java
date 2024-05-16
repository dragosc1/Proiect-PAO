package Classes.Publication;

import Classes.User.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Author {
    private int id;
    private String name;
    private String firstName;

    // Author constructor
    public Author(int id, String lastName, String firstName) {
        this.id = id;
        this.name = lastName;
        this.firstName = firstName;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Author equals operator
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Author other = (Author) obj;
        return Objects.equals(id, other.id) &&
                Objects.equals(name, other.name) &&
                Objects.equals(firstName, other.firstName);
    }

    // Author string representation
    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", lastName='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, firstName);
    }

    // Author parse result set
    public static Author parseResultSet(ResultSet resultSet) throws SQLException {
        // gather data
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String firstname = resultSet.getString("firstname");

        // return author
        return new Author(id, name, firstname);
    }
}