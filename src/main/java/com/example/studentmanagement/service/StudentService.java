package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentRepo studentRepo;

    public Page<Student> getAllStudent(int pageNumber){
        PageRequest pageRequest = PageRequest.of(pageNumber-1, 5);
        Page<Student> studentPage = studentRepo.findAll(pageRequest);
        return studentPage;
    }
}
