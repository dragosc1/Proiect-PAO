package Classes.Publication;

import java.util.Objects;

// Section class
public class Section {
    private String name;

    public Section(String name) {
        this.name = name;
    }

    // Getter and setter for the section name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return Objects.equals(name, section.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
