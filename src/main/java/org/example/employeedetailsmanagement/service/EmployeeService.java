package org.example.employeedetailsmanagement.service;

import org.example.employeedetailsmanagement.entity.Employee;
import org.example.employeedetailsmanagement.exception.ResourceNotFoundException;
import org.example.employeedetailsmanagement.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;


    public List<Employee> getEmployees() {
        List<Employee> employees = employeeRepo.findAll();
        return employees;
    }

    public Optional<Employee> getEmployeesById(Long id) {
        Optional<Employee> employees = employeeRepo.findById(id);
        return employees;
    }

    public Employee updateUser(Long id, Employee employee) {
        Employee currentEmployee = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id : "+ id));

        currentEmployee.setFirstName(employee.getFirstName());
        currentEmployee.setLastName(employee.getLastName());
        currentEmployee.setEmailAddress(employee.getEmailAddress());
        currentEmployee.setPhone(employee.getPhone());
        currentEmployee.setBirthdate(employee.getBirthdate());
        currentEmployee.setJobTitle(employee.getJobTitle());
        currentEmployee.setDepartment(employee.getDepartment());
        currentEmployee.setLocation(employee.getLocation());
        currentEmployee.setStartDate(employee.getStartDate());
        currentEmployee.setManagerReporting(employee.getManagerReporting());

        final Employee updatedEmployee  = employeeRepo.save(currentEmployee);
        return updatedEmployee;
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id : "+ id));

        employeeRepo.delete(employee);
    }

    public Employee save(Employee employee) {
        return employeeRepo.save(employee);
    }
}

