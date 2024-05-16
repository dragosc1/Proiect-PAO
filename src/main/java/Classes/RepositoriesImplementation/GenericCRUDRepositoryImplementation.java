package Classes.RepositoriesImplementation;

import Classes.Publication.Publication;
import Classes.Repositories.GenericCRUDRepository;
import Classes.Services.AuditService;
import Classes.Services.GenericCRUDService;
import oracle.jdbc.datasource.impl.OracleDataSource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

public class GenericCRUDRepositoryImplementation<T> implements GenericCRUDRepository<T> {
    private Connection connection;
    public GenericCRUDRepositoryImplementation() {
    }
    private String getTableName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase();
    }

    // insert data (CREATE)
    public void insert(T data) {
        try {
            // get table name of T
            Class<?> clazz = data.getClass();
            String tableName = getTableName(clazz).toUpperCase();
            tableName = "\"" + tableName + "\"";

            // get all fields from base and supperclass of T (if it exists)
            List<Field> allFields = new ArrayList<>();
            Collections.addAll(allFields, clazz.getDeclaredFields());

            Class<?> superClass = clazz.getSuperclass();
            while (superClass != null) {
                Collections.addAll(allFields, superClass.getDeclaredFields());
                superClass = superClass.getSuperclass();
            }

            Field[] fields = allFields.toArray(new Field[0]);

            // get all table data fields which has name of tableName
            GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
            publicationService.openConnection();
            ResultSetMetaData metaData = connection.createStatement().executeQuery("SELECT * FROM " + tableName).getMetaData();
            int columnCount = metaData.getColumnCount();
            Set<String> tableColumnNames = new HashSet<>();
            for (int i = 1; i <= columnCount; i++)
                tableColumnNames.add(metaData.getColumnName(i).toLowerCase());

            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();

            // insert to table columns data
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
                        else statement.setTimestamp(index++, new Timestamp(((Date) field.get(data)).getTime()));
                    } else {
                        statement.setObject(index++, field.get(data));
                    }
                }
            }

            statement.executeUpdate();
            publicationService.closeConnection();

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
            // get table name of T
            String tableName = getTableName(clazz).toUpperCase();
            tableName = "\"" + tableName + "\"";
            String sql = "SELECT * FROM " + tableName;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // return and parse result set as List<T>
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
            // get table name of T
            String tableName = getTableName(clazz).toUpperCase();
            tableName = "\"" + tableName + "\"";
            String sql = "SELECT * FROM " + tableName + " WHERE id = " + id;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // return and parse result set as T

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
            // get table name of T
            Class<?> clazz = data.getClass();
            String tableName = getTableName(clazz).toUpperCase();
            tableName = "\"" + tableName + "\"";

            // get all fields from base and supperclass of T (if it exists)
            List<Field> allFields = new ArrayList<>();
            Collections.addAll(allFields, clazz.getDeclaredFields());

            Class<?> superClass = clazz.getSuperclass();
            while (superClass != null) {
                Collections.addAll(allFields, superClass.getDeclaredFields());
                superClass = superClass.getSuperclass();
            }

            Field[] fields = allFields.toArray(new Field[0]);
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

            // delete the data
            String sql = "DELETE FROM " + tableName + " WHERE " + condition.toString();
            PreparedStatement statement = connection.prepareStatement(sql);

            int index = 1;
            for (Field field : fields) {
                String fieldName = field.getName().toLowerCase();
                if (tableColumnNames.contains(fieldName)) {
                    field.setAccessible(true);
                    if (field.getType() == Date.class) {
                        if (field.get(data) == null)
                            statement.setObject(index++, null);
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
            // get table name
            String tableName = getTableName(clazz).toUpperCase();
            tableName = "\"" + tableName + "\"";

            // delete all from table
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
            // get table name
            Class<?> clazz = oldData.getClass();
            String tableName = getTableName(clazz).toUpperCase();
            tableName = "\"" + tableName + "\"";

            // get all fields from base and supperclass of T (if it exists)
            List<Field> allFields = new ArrayList<>();
            Collections.addAll(allFields, clazz.getDeclaredFields());

            Class<?> superClass = clazz.getSuperclass();
            while (superClass != null) {
                Collections.addAll(allFields, superClass.getDeclaredFields());
                superClass = superClass.getSuperclass();
            }

            Field[] fields = allFields.toArray(new Field[0]);
            GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
            publicationService.openConnection();
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
            // update the data

            String condition = "id=?"; // Assuming 'id' is the primary key field
            String sql = "UPDATE " + tableName + " SET " + setValues.toString() + " WHERE " + condition;
            PreparedStatement statement = connection.prepareStatement(sql);

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
            publicationService.closeConnection();

        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
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
