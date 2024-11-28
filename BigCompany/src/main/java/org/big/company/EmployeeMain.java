package org.big.company;

import org.big.company.dto.EmployeeDTO;
import org.big.company.service.EmployeeService;
import org.big.company.utility.CSVFileReaderUtility;

import java.util.List;

public class EmployeeMain {
    public static void main(String[] args) throws Exception {

        String filePath = "employeedetails.csv"; // path relative to resource folder
        List<EmployeeDTO> employees = CSVFileReaderUtility.readCSVFile(filePath);

        EmployeeService service = new EmployeeService(employees);
        service.initiateProcess();
    }
}