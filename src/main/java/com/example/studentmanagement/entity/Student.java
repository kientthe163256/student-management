package com.example.studentmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Entity
@Table(name = "student")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Firstname can not be empty")
    private String firstname;

    @NotBlank(message = "Lastname can not be blank")
    private String lastname;

    @NotNull(message = "Age can not be null")
    @Min(value = 1, message = "Age must be > 1")
    private Integer age;

}
