package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Classroom;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.ClassroomRepo;
import com.example.studentmanagement.repository.StudentRepo;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClassroomService {
    @Autowired
    private ClassroomRepo classroomRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private StudentService studentService;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void deleteClass(int classId){
        List<Student> students = studentService.getStudentByClassID(classId);
        students.forEach(student -> {studentRepo.delete(student);});
        classroomRepo.deleteById(classId);
        //If no custom rollback rules apply, the transaction
        // will roll back on RuntimeException and Error but not on checked exceptions
        throw new RuntimeException("RuntimeException in deleteClass service");
    }

    public List<Classroom> getAllClassroom(boolean isDeleted){
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedClassroomFilter");
        filter.setParameter("isDeleted", isDeleted);
        //try with resource

        List<Classroom> classrooms = classroomRepo.findAll();

        session.disableFilter("deletedStudentFilter");
        return classrooms;
    }

    public Classroom getClassroomById(int id){
        return classroomRepo.findById(id).get();
    }

    public Classroom recoverClassroom(int id){
        Optional<Classroom> c = classroomRepo.findById(id);
        if (c.isPresent()){
            Classroom classroom = c.get();
            classroom.setDeleted(false);
            classroomRepo.save(classroom);
            return classroom;
        }
        else return null;
    }
}
