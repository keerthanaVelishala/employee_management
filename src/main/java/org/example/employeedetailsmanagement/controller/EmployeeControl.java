package org.example.employeedetailsmanagement.controller;


import org.example.employeedetailsmanagement.entity.Employee;
import org.example.employeedetailsmanagement.exception.ResourceNotFoundException;
import org.example.employeedetailsmanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/")
@RestController
public class EmployeeControl {

    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> employees = employeeService.getEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<List<Employee>> getEmployeeById(@PathVariable Long id ){
        List<Employee> employees = new ArrayList<>();
        Employee employee =employeeService.getEmployeesById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found for this id : "+ id));
        employees.add(employee);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<List<Employee>> deleteEmployeeById(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        Employee createdEmployee = employeeService.save(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.OK);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateUser(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.updateUser(id, employee);
        return ResponseEntity.ok(updatedEmployee);
    }


}
