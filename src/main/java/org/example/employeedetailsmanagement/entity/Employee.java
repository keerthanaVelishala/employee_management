package org.example.employeedetailsmanagement.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phone;
    private Date birthdate;
    private String jobTitle;
    private String department;
    private String location;
    private Date startDate;
    private String managerReporting;

}
