package com.example.studentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Getter
@Setter
@Entity
@Table(name = "student")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE student SET deleted = true WHERE id=?")
@FilterDef(name = "deletedStudentFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedStudentFilter", condition = "deleted = :isDeleted")
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

    private String email;

    private boolean deleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "class_id")
//    @JsonBackReference
    @JsonManagedReference
    private Classroom classroom;
}
