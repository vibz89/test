package org.big.company;

import org.big.company.dto.EmployeeDTO;
import org.big.company.service.EmployeeService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class EmployeeMainTest {
    @Test
    public void testManagerSalariesWhichEarnsLessThanRequired() throws Exception {
        List<EmployeeDTO> employees = Arrays.asList(
                new EmployeeDTO(123, "Joe", "Doe", BigDecimal.valueOf(60000), null),
                new EmployeeDTO(124, "Martin", "Chekov", BigDecimal.valueOf(45000), 123),
                new EmployeeDTO(125, "Bob", "Ronstad", BigDecimal.valueOf(47000), 123),
                new EmployeeDTO(300, "Alice", "Hasacat", BigDecimal.valueOf(50000), 124),
                new EmployeeDTO(305, "Bret", "Hardleaf", BigDecimal.valueOf(34000), 300)
        );
        EmployeeService servicetest = new EmployeeService(employees);
        servicetest.initiateProcess();
    }

    @Test
    public void testManagerSalariesWhichEarnsMoreThanRequired() throws Exception {
        List<EmployeeDTO> employees = Arrays.asList(
                new EmployeeDTO(123, "Joe", "Doe", BigDecimal.valueOf(60000), null),
                new EmployeeDTO(124, "Martin", "Chekov", BigDecimal.valueOf(45000), 123),
                new EmployeeDTO(125, "Bob", "Ronstad", BigDecimal.valueOf(47000), 123),
                new EmployeeDTO(300, "Alice", "Hasacat", BigDecimal.valueOf(50000), 123),
                new EmployeeDTO(305, "Bret", "Hardleaf", BigDecimal.valueOf(33000), 300)
        );
        EmployeeService servicetest = new EmployeeService(employees);
        servicetest.initiateProcess();
    }

    @Test
    public void testManagerSalariesWhichEarnsMoreAndLessThanRequired() throws Exception {
        List<EmployeeDTO> employees = Arrays.asList(
                new EmployeeDTO(123, "Joe", "Doe", BigDecimal.valueOf(60000), null),
                new EmployeeDTO(124, "Martin", "Chekov", BigDecimal.valueOf(45000), 123),
                new EmployeeDTO(125, "Bob", "Ronstad", BigDecimal.valueOf(47000), 123),
                new EmployeeDTO(300, "Alice", "Hasacat", BigDecimal.valueOf(50000), 125),
                new EmployeeDTO(305, "Bret", "Hardleaf", BigDecimal.valueOf(33000), 300)
        );
        EmployeeService servicetest = new EmployeeService(employees);
        servicetest.initiateProcess();
    }

    @Test
    public void testEmployeeWithReportingLine() throws Exception {
        List<EmployeeDTO> employees = Arrays.asList(
                new EmployeeDTO(123, "Joe", "Doe", BigDecimal.valueOf(80000), null),
                new EmployeeDTO(124, "Martin", "Chekov", BigDecimal.valueOf(65000), 123),
                new EmployeeDTO(125, "Bob", "Ronstad", BigDecimal.valueOf(52000), 124),
                new EmployeeDTO(300, "Alice", "Hasacat", BigDecimal.valueOf(43000), 125),
                new EmployeeDTO(305, "Bret", "Hardleaf", BigDecimal.valueOf(30000), 300),
                new EmployeeDTO(306, "Rachel", "Kickoff", BigDecimal.valueOf(22000), 305)
        );
        EmployeeService servicetest = new EmployeeService(employees);
        servicetest.initiateProcess();
    }

    @Test
    public void testAllUseCase() throws Exception {
        List<EmployeeDTO> employees = Arrays.asList(
                new EmployeeDTO(123, "Joe", "Doe", BigDecimal.valueOf(70000), null),
                new EmployeeDTO(124, "Martin", "Chekov", BigDecimal.valueOf(65000), 123),
                new EmployeeDTO(125, "Bob", "Ronstad", BigDecimal.valueOf(52000), 124),
                new EmployeeDTO(300, "Alice", "Hasacat", BigDecimal.valueOf(43000), 125),
                new EmployeeDTO(305, "Bret", "Hardleaf", BigDecimal.valueOf(27000), 300),
                new EmployeeDTO(306, "Rachel", "Kickoff", BigDecimal.valueOf(22000), 305)
        );
        EmployeeService servicetest = new EmployeeService(employees);
        servicetest.initiateProcess();
    }
}