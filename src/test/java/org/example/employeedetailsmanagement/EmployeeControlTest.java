package org.example.employeedetailsmanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.employeedetailsmanagement.controller.EmployeeControl;
import org.example.employeedetailsmanagement.entity.Employee;
import org.example.employeedetailsmanagement.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControlTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeControl employeeControl;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(employeeControl).build();
    }

    @Test
    public void getAllEmployeesTest() throws Exception {
        Employee employee1 = new Employee(1L, "Akshay", "Kumar", "Akshay.kumar@example.com", "123-456-7890", null, "Software Engineer", "Engineering", "New York", null, "Jane");
        Employee employee2 = new Employee(2L, "Sai", "Kumar", "sai.kumar@example.com", "098-765-4321", null, "Project Manager", "Management", "New York", null, "Joe");

        when(employeeService.getEmployees()).thenReturn(Arrays.asList(employee1, employee2));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].employeeId").value(employee1.getEmployeeId()))
                .andExpect(jsonPath("$[1].employeeId").value(employee2.getEmployeeId()));
    }

    @Test
    public void getEmployeeByIdTest() throws Exception {
        Long id = 1L;
        Employee employee = new Employee(1L, "Akshay", "Kumar", "Akshay.kumar@example.com", "123-456-7890", null, "Software Engineer", "Engineering", "New York", null, "Jane");
        given(employeeService.getEmployeesById(id)).willReturn(Optional.of(employee));

        mockMvc.perform(get("/employees/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].employeeId").value(id));
    }

    @Test
    public void createEmployeeTest() throws Exception {
        Employee newEmployee = new Employee(null, "Krishna", "Sai", "krishna.sai@example.com", "123-456-7890", null, "Developer", "Engineering", "New York", null, "John Doe");
        Employee savedEmployee = new Employee(3L, "Krishna", "Sai", "krishna.sai@example.com", "123-456-7890", null, "Developer", "Engineering", "New York", null, "John Doe");

        when(employeeService.save(any(Employee.class))).thenReturn(savedEmployee);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEmployee)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employeeId").value(savedEmployee.getEmployeeId()));
    }

    @Test
    public void updateEmployeeTest() throws Exception {
        Long id = 1L;
        Employee employeeToUpdate = new Employee(id, "Akshay", "Kumar", "Akshay.kumar@example.com", "123-456-7890", null, "Software Engineer", "Engineering", "New York", null, "Jane");
        Employee updatedEmployee = new Employee(id, "Akshay", "shetty", "Akshay.shetty@example.com", "123-456-7890", null, "Software Engineer", "Engineering", "New York", null, "Raj kumar");

        given(employeeService.updateUser(id, employeeToUpdate)).willReturn(updatedEmployee);

        mockMvc.perform(put("/employees/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeToUpdate)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lastName").value("shetty"))
                .andExpect(jsonPath("$.emailAddress").value("Akshay.shetty@example.com"));
    }

    @Test
    public void deleteEmployeeByIdTest() throws Exception {
        Long id = 1L;

        doNothing().when(employeeService).deleteEmployee(id);

        mockMvc.perform(delete("/employees/{id}", id))
                .andExpect(status().isNoContent());

        verify(employeeService, times(1)).deleteEmployee(id);
    }
}
