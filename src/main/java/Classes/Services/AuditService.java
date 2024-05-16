package Classes.Services;

import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class AuditService {
    // Singleton pattern
    private static AuditService instance;

    public static synchronized AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }
    public AuditService() {
    }

    // write to CSV
    public void writeToOperationsCSV(List<String> newData) {
        String operationsFilePath = "output/operations.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(operationsFilePath, true))) {
            int index = 0;
            int size = newData.size();
            for (String entry : newData) {
                writer.write(entry);
                index++;
                if (index != size)
                    writer.write(", \t");
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
