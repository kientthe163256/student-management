package com.example.studentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name = "classroom")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE classroom SET deleted = true WHERE id=?")
@FilterDef(name = "deletedClassroomFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedClassroomFilter", condition = "deleted = :isDeleted")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "ClassName can't be blank")
    @Size(min = 4, max = 4, message = "ClassName must be 4 characters")
    @Column(name = "classname")
    private String className;

    @NotBlank(message = "Description can't be empty")
    private String description;

    private boolean deleted = Boolean.FALSE;


    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL)
//    @JsonManagedReference
    @JsonBackReference
    private Collection<Student> students;
}
