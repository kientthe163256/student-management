package com.example.studentmanagement.response;

import com.example.studentmanagement.entity.Classroom;
import lombok.Builder;

import java.io.Serializable;
@Builder
public class StudentResponse implements Serializable {
    private String email;
    private String fullname;
    private String classroom;
    private int age;
}
