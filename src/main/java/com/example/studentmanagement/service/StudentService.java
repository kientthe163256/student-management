package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.StudentRepo;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


@Service
public class StudentService {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private StudentRepo studentRepo;

    public Page<Student> getAllStudent(int pageNumber){
        PageRequest pageRequest = PageRequest.of(pageNumber-1, 5);
        Page<Student> studentPage = studentRepo.findAll(pageRequest);
        return studentPage;
    }

    public Page<Student> getAllStudentWithFilter(int pageNumber, boolean isDeleted){
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedStudentFilter");
        filter.setParameter("isDeleted", isDeleted);

        PageRequest pageRequest = PageRequest.of(pageNumber-1, 5);
        Page<Student> studentPage = studentRepo.findAll(pageRequest);

        session.disableFilter("deletedStudentFilter");
        return studentPage;
    }


    public List<Student> getStudentByClassID(int classId){
        List<Student> allStudentList = studentRepo.findAll();
        List<Student> studentList = new ArrayList<>();
        for (Student s : allStudentList){
            if (s.getClassroom().getId() == classId){
                studentList.add(s);
            }
        }
        return studentList;
    }

    public Student recoverDeletedStudent(int id){
        studentRepo.recoverStudent(id);
        Student s = studentRepo.findStudentById(id);
        return s;
    }

}
