package Classes.RepositoriesImplementation;

import Classes.Repositories.GenericCRUDRepository;
import oracle.jdbc.datasource.impl.OracleDataSource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class GenericCRUDRepositoryImplementation<T> implements GenericCRUDRepository<T> {
    private Connection connection;
    public GenericCRUDRepositoryImplementation() {
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
    private String getTableName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase();
    }

    public void insert(T data) {
        try {
            Class<?> clazz = data.getClass();
            String tableName = getTableName(clazz).toUpperCase();
            tableName = "\"" + tableName + "\"";

            List<Field> allFields = new ArrayList<>();
            Collections.addAll(allFields, clazz.getDeclaredFields());

            Class<?> superClass = clazz.getSuperclass();
            while (superClass != null) {
                Collections.addAll(allFields, superClass.getDeclaredFields());
                superClass = superClass.getSuperclass();
            }

            Field[] fields = allFields.toArray(new Field[0]);
            // Get metadata of the table to retrieve its column names
            ResultSetMetaData metaData = connection.createStatement().executeQuery("SELECT * FROM " + tableName).getMetaData();
            int columnCount = metaData.getColumnCount();

            Set<String> tableColumnNames = new HashSet<>();
            for (int i = 1; i <= columnCount; i++)
                tableColumnNames.add(metaData.getColumnName(i).toLowerCase());

            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();

            for (Field field : fields) {
                String fieldName = field.getName().toLowerCase();
                if (tableColumnNames.contains(fieldName)) {
                    columns.append(field.getName()).append(",");
                    values.append("?,");
                }
            }
            if (columns.length() > 0) {
                columns.deleteCharAt(columns.length() - 1);
                values.deleteCharAt(values.length() - 1);
            }
            String sql = "INSERT INTO " + tableName + " (" + columns.toString() + ") VALUES (" + values.toString() + ")";
            PreparedStatement statement = connection.prepareStatement(sql);

            int index = 1;
            for (Field field : fields) {
                String fieldName = field.getName().toLowerCase();
                if (tableColumnNames.contains(fieldName)) {
                    field.setAccessible(true);
                    if (field.getType() == Date.class) {
                        if (field.get(data) == null)
                            statement.setObject(index++, null);
                            // If the field is of type Date, use setTimestamp instead of setObject
                        else statement.setTimestamp(index++, new Timestamp(((Date) field.get(data)).getTime()));
                    } else {
                        statement.setObject(index++, field.get(data));
                    }
                }
            }

            statement.executeUpdate();
        } catch (SQLException  e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to retrieve data from the database
    public List<T> retrieveAll(Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        try {
            String tableName = getTableName(clazz).toUpperCase();
            tableName = "\"" + tableName + "\"";
            String sql = "SELECT * FROM " + tableName;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            Method parseMethod = clazz.getDeclaredMethod("parseResultSet", ResultSet.class);

            while (resultSet.next()) {
                T instance = clazz.cast(parseMethod.invoke(null, resultSet));
                result.add(instance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    public T retrieveOneId(Class<T> clazz, int id) {
        T result = null;
        try {
            String tableName = getTableName(clazz).toUpperCase();
            tableName = "\"" + tableName + "\"";
            String sql = "SELECT * FROM " + tableName + " WHERE id = " + id;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            Method parseMethod = clazz.getDeclaredMethod("parseResultSet", ResultSet.class);

            if (resultSet.next()) {
                T instance = clazz.cast(parseMethod.invoke(null, resultSet));
                result = instance;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    // Method to delete data from database
    public void delete(T data) {
        try {
            Class<?> clazz = data.getClass();
            String tableName = getTableName(clazz).toUpperCase();
            tableName = "\"" + tableName + "\"";

            List<Field> allFields = new ArrayList<>();
            Collections.addAll(allFields, clazz.getDeclaredFields());

            Class<?> superClass = clazz.getSuperclass();
            while (superClass != null) {
                Collections.addAll(allFields, superClass.getDeclaredFields());
                superClass = superClass.getSuperclass();
            }

            Field[] fields = allFields.toArray(new Field[0]);
            // Get metadata of the table to retrieve its column names
            ResultSetMetaData metaData = connection.createStatement().executeQuery("SELECT * FROM " + tableName).getMetaData();
            int columnCount = metaData.getColumnCount();

            Set<String> tableColumnNames = new HashSet<>();
            for (int i = 1; i <= columnCount; i++)
                tableColumnNames.add(metaData.getColumnName(i).toLowerCase());

            StringBuilder condition = new StringBuilder();
            for (Field field : fields) {
                String fieldName = field.getName().toLowerCase();
                if (tableColumnNames.contains(fieldName)) {
                    field.setAccessible(true);
                    condition.append(field.getName()).append("=? AND ");
                }
            }
            // Remove the trailing "AND"
            if (condition.length() > 0) {
                condition.setLength(condition.length() - 5);
            }

            String sql = "DELETE FROM " + tableName + " WHERE " + condition.toString();
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set parameter values based on the fields of the data object
            int index = 1;
            for (Field field : fields) {
                String fieldName = field.getName().toLowerCase();
                if (tableColumnNames.contains(fieldName)) {
                    field.setAccessible(true);
                    if (field.getType() == Date.class) {
                        if (field.get(data) == null)
                            statement.setObject(index++, null);
                            // If the field is of type Date, use setTimestamp instead of setObject
                        else statement.setTimestamp(index++, new Timestamp(((Date) field.get(data)).getTime()));
                    } else {
                        statement.setObject(index++, field.get(data));
                    }
                }
            }

            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll(Class<?> clazz) {
        try {
            String tableName = getTableName(clazz).toUpperCase();
            tableName = "\"" + tableName + "\"";

            String sql = "DELETE FROM " + tableName;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();

            System.out.println("All data from table " + tableName + " deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update data from the database
    public void update(T oldData, T newData) {
        try {
            Class<?> clazz = oldData.getClass();
            String tableName = getTableName(clazz).toUpperCase();
            tableName = "\"" + tableName + "\"";

            List<Field> allFields = new ArrayList<>();
            Collections.addAll(allFields, clazz.getDeclaredFields());

            Class<?> superClass = clazz.getSuperclass();
            while (superClass != null) {
                Collections.addAll(allFields, superClass.getDeclaredFields());
                superClass = superClass.getSuperclass();
            }

            Field[] fields = allFields.toArray(new Field[0]);
            // Get metadata of the table to retrieve its column names
            ResultSetMetaData metaData = connection.createStatement().executeQuery("SELECT * FROM " + tableName).getMetaData();
            int columnCount = metaData.getColumnCount();

            Set<String> tableColumnNames = new HashSet<>();
            for (int i = 1; i <= columnCount; i++)
                tableColumnNames.add(metaData.getColumnName(i).toLowerCase());

            StringBuilder setValues = new StringBuilder();
            for (Field field : fields) {
                String fieldName = field.getName().toLowerCase();
                if (tableColumnNames.contains(fieldName)) {
                    field.setAccessible(true);
                    setValues.append(field.getName()).append("=?,");
                }
            }
            // Remove the trailing comma
            if (!setValues.isEmpty()) {
                setValues.deleteCharAt(setValues.length() - 1);
            }

            String condition = "id=?"; // Assuming 'id' is the primary key field
            String sql = "UPDATE " + tableName + " SET " + setValues.toString() + " WHERE " + condition;
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set parameter values based on the fields of the newData object
            int index = 1;
            for (Field field : fields) {
                String fieldName = field.getName().toLowerCase();
                if (tableColumnNames.contains(fieldName)) {
                    field.setAccessible(true);
                    if (field.getName() == "id")
                        statement.setObject(index++, field.get(oldData));
                    else if (field.getType() == Date.class) {
                        if (field.get(newData) == null)
                            statement.setObject(index++, null);
                            // If the field is of type Date, use setTimestamp instead of setObject
                        else statement.setTimestamp(index++, new Timestamp(((Date) field.get(newData)).getTime()));
                    } else {
                        statement.setObject(index++, field.get(newData));
                    }
                }
            }

            // Set id parameter value (assuming 'id' is the primary key field)
            Field idField = clazz.getDeclaredField("id");
            idField.setAccessible(true);

            statement.setObject(index, idField.get(oldData));

            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
