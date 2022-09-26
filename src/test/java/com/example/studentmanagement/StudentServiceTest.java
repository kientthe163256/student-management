package com.example.studentmanagement;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class StudentServiceTest {
    @Autowired
    private StudentService studentService;

    @Test
    public void testGetStudentPaging(){
        Page<Student> studentPage = studentService.getAllStudent(1);
        assertThat(studentPage).isNotNull();
    }

    @Test
    public void testGetDeletedStudent(){
//        Page<Student> students = studentService.getAllStudentWithFilter(1, true);
        Page<Student> students = studentService.getAllStudentWithFilter(1, true);
        assertThat(students).isNotNull();
    }

    @Test
    public void testRecoverDeletedStudent(){
        Student s = studentService.recoverDeletedStudent(21);
        assertThat(s.isDeleted()).isFalse();
    }
}
