package com.example.studentmanagement;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.StudentRepo;
import com.example.studentmanagement.service.StudentService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Transactional
@SpringBootTest

//@RunWith(SpringRunner.class)
public class StudentRepoTest {
    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private StudentService studentService;

    @Test
    public void getAllStudent() {
        List<Student> studentList = studentRepo.findAll();
        Student a = studentList.get(0);
        System.out.println(a.getClassroom().getClassName());
        Assertions.assertThat(studentList).isNotNull();
    }

    @Test
    public void deleteStudent() {
        int sizeBefore = studentRepo.findAll().size();
        studentRepo.deleteById(3);
        int sizeAfter = studentRepo.findAll().size();
        assertThat(sizeBefore).isGreaterThan(sizeAfter);
    }

    @Test
    public void saveStudent() {
        Student s = Student.builder()
                .firstname("Mister")
                .lastname("SuperMuscle2")
                .age(15)
                .build();

        studentRepo.save(s);
        assertThat(s.getId()).isGreaterThan(0);
    }

    @Test
    public void updateStudent() {
        Student s = studentRepo.findById(3).get();
        s.setAge(999);
        studentRepo.save(s);
        assertThat(s.getAge()).isEqualTo(999);
    }

    @Test
    public void updateStudentInvalidAge() {
        Student s = studentRepo.findById(3).get();
//        s.setAge("agra");
        studentRepo.save(s);
        assertThat(s.getAge()).isEqualTo(999);
    }

    @Test
    public void getStudentByID() {
        Optional<Student> s = studentRepo.findById(0);
        try {
            Student a = s.get();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void getStudentByIDService() {
        try {
            Page<Student> student = studentService.getAllStudent(1);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void searchStudent(){
        List<Student> students = studentRepo.searchStudent("tr");
        assertThat(students).isNotNull();
    }
}
