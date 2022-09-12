package com.example.studentmanagement;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.StudentRepo;
import lombok.Data;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class StudentRepoTest {
    @Autowired
    private StudentRepo studentRepo;

    @Test
    public void getAllStudent(){
        List<Student> studentList = studentRepo.findAll();
        Assertions.assertThat(studentList).isNotNull();
    }

    @Test
    public void deleteStudent(){
        int sizeBefore = studentRepo.findAll().size();
        studentRepo.deleteById(3);
        int sizeAfter = studentRepo.findAll().size();
        assertThat(sizeBefore).isGreaterThan(sizeAfter);
    }

    @Test
    public void saveStudent(){
        Student s = Student.builder()
            .firstname("Mister")
            .lastname("SuperMuscle2")
            .age(15)
        .build();

        studentRepo.save(s);
        assertThat(s.getId()).isGreaterThan(0);
    }

    @Test
    public void updateStudent(){
        Student s = studentRepo.findById(3).get();
        s.setAge(999);
        studentRepo.save(s);
        assertThat(s.getAge()).isEqualTo(999);
    }

    @Test
    public void updateStudentInvalidAge(){
        Student s = studentRepo.findById(3).get();
//        s.setAge("agra");
        studentRepo.save(s);
        assertThat(s.getAge()).isEqualTo(999);
    }

    @Test
    public void getStudentByID(){
        Student s = studentRepo.findById(1).get();
    }
}
