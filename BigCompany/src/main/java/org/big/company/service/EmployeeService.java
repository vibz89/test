package org.big.company.service;

import org.big.company.dto.EmployeeDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class EmployeeService {

    private static final Logger LOGGER = Logger.getLogger(EmployeeService.class.getName());

    private final Map<Integer, List<EmployeeDTO>> managerToSubordinatesDetail = new HashMap<>();
    private final Map<Integer, EmployeeDTO> employeeDetailsById = new HashMap<>();
    private final Map<Integer, Integer> reportingLineDepthCountDetails = new HashMap<>();


    public EmployeeService(List<EmployeeDTO> employees) {
        for (EmployeeDTO employee : employees) {
            employeeDetailsById.put(employee.getId(), employee);
            managerToSubordinatesDetail
                    .computeIfAbsent(employee.getManagerId(), k -> new ArrayList<>())
                    .add(employee);
        }
    }

    public void initiateProcess() {
        try {
            checkManagerSalaries();
            checkReportingHierarchy();
        } catch (Exception e) {
            LOGGER.severe(String.format("Error during analysis: %s" ,e.getMessage()));
        }
    }


    private void checkReportingHierarchy() {
        for (EmployeeDTO employee : employeeDetailsById.values()) {
            int depth = getReportingLineDepthDetails(employee.getId());
            if (depth > 4) {
                LOGGER.info(String.format("Employee - %s has a reporting line too long by %d levels", employee.getFirstName(), depth - 4));
            }
        }
    }

    private int getReportingLineDepthDetails(int employeeId) {

        if (reportingLineDepthCountDetails.containsKey(employeeId)) {
            return reportingLineDepthCountDetails.get(employeeId);
        }

        // checking the reporting hierarchy
        int depth = 0;
        Integer managerId = employeeDetailsById.get(employeeId).getManagerId();

        while (managerId != null) {

            if (reportingLineDepthCountDetails.containsKey(managerId)) {
                depth += reportingLineDepthCountDetails.get(managerId) + 1;
                break;
            }
            if (!employeeDetailsById.containsKey(managerId)) {
                LOGGER.warning(String.format("Manager ID - %s not found for employee ID - %s" ,managerId, employeeId));
                break;
            }

            depth++;
            managerId = employeeDetailsById.get(managerId).getManagerId();
        }

        // Cache the computed depth for the current employee
        reportingLineDepthCountDetails.put(employeeId, depth);
        return depth;
    }

    private void checkManagerSalaries() {
        for (Map.Entry<Integer, List<EmployeeDTO>> entry : managerToSubordinatesDetail.entrySet()) {
            Integer managerId = entry.getKey();
            List<EmployeeDTO> subordinates = entry.getValue();

            if (managerId == null || subordinates.isEmpty()) {continue;}

            EmployeeDTO manager = employeeDetailsById.get(managerId);
            if (manager == null) {
                LOGGER.warning(String.format("Manager ID - %s not found in employee records.",managerId));
                continue;
            }

            // Calculate subordinates salary
            BigDecimal totalSalary = subordinates.stream()
                    .map(EmployeeDTO::getSalary)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal avgSalary = subordinates.isEmpty()
                    ? BigDecimal.ZERO
                    : totalSalary.divide(BigDecimal.valueOf(subordinates.size()), BigDecimal.ROUND_HALF_UP);

            BigDecimal minSalary = avgSalary.multiply(BigDecimal.valueOf(1.2)); // 1.2 represent 20% increase
            BigDecimal maxSalary = avgSalary.multiply(BigDecimal.valueOf(1.5));// 1.5 represent 50% increase

            if (manager.getSalary().compareTo(minSalary) < 0) {
                BigDecimal diffValue = minSalary.subtract(manager.getSalary());
                LOGGER.info(String.format("Manager - %s earns less than required by %.2f",manager.getFirstName(), diffValue));
            } else if (manager.getSalary().compareTo(maxSalary) > 0) {
                BigDecimal excessValue = manager.getSalary().subtract(maxSalary);
                LOGGER.info(String.format("Manager - %s earns more than allowed by %.2f",manager.getFirstName(), excessValue));
            }
        }
    }

}
