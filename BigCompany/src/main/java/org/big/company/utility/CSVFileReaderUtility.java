package org.big.company.utility;

import org.big.company.dto.EmployeeDTO;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVFileReaderUtility {

    public static List<EmployeeDTO> readCSVFile(String filePath) throws Exception {

        try (InputStream inputStream = CSVFileReaderUtility.class.getClassLoader().getResourceAsStream(filePath)) {
//        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            if (inputStream == null) {
                System.out.println("File not found in resources");
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            List<EmployeeDTO> employees = new ArrayList<>();

            reader.readLine();// consider first line as header
            reader.lines().map(line -> {
                        try {
                            return parseLineToEmployeeDTO(line);
                        } catch (Exception e) {
                            System.err.println("Error while parsing line: " + line);
                            return null;
                        }
                    }).filter(employee -> employee != null)
                    .forEach(employees::add);

            return employees;
        }
    }

    private static EmployeeDTO parseLineToEmployeeDTO(String line) {

        String[] parts = line.split(",", -1);
        Integer id = Integer.parseInt(parts[0]);
        String firstName = parts[1];
        String lastName = parts[2];
        BigDecimal salary = new BigDecimal(parts[3]);
        Integer managerId = parts[4].isEmpty() ? null : Integer.parseInt(parts[4]);

        return new EmployeeDTO(id, firstName, lastName, salary, managerId);
    }

}
