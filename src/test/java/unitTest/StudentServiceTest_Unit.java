package unitTest;

import com.example.spring_crud_um.student.model.Student;
import com.example.spring_crud_um.student.repository.StudentRepository;
import com.example.spring_crud_um.student.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest_Unit {

    @Mock
    private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @Test
    void canGetAllStudents() {
        //When
        underTest.getAllStudents();

        //Then
        verify(studentRepository).findAll();
    }

    @Test
    void canGetStudentsById() {

        //Given
        List<Student> student = new ArrayList<>();
        student.add(new Student (1L,"Test","test@gmail.com", LocalDate.now(),0));

        Mockito.when(studentRepository.findStudentById(student.get(0).getId())).thenReturn(student);
        Mockito.when(studentRepository.existsById(student.get(0).getId())).thenReturn(true);

        //When
        underTest.getStudentsById(student.get(0).getId());

        //Then
        verify(studentRepository).findStudentById(student.get(0).getId());
    }


    @Test
    void canAddNewStudent() {
        //Given
        Student student =new Student (1L,"Test","test@gmail.com", LocalDate.now(),0);

        //When
        underTest.addNewStudent(student);

        //Then
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(student);
    }
    @Test
    void willThrowWhenEmailIsTaken() {
        //Given
        Student student =new Student (1L,"Test","test@gmail.com", LocalDate.now(),0);
        Optional<Student> studentOptional = Optional.of(new Student(1L, "Test", "test@gmail.com", LocalDate.now(), 0));

        BDDMockito.given(studentRepository.findStudentByEmail(studentOptional.get().getEmail()))
        .willReturn(studentOptional);
        //When
        //Then
        assertThatThrownBy(() ->underTest.addNewStudent(student))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email already exists");

        verify(studentRepository, never()).save(student);

    }
    @Test
    void canDeleteStudent() {
        // given
        Student student =new Student (1L,"Test","test@gmail.com", LocalDate.now(),0);

        studentRepository.save(student);

        BDDMockito.given(studentRepository.existsById(student.getId())).willReturn(true);

        // when
        underTest.deleteStudent(student.getId());

        // then
        verify(studentRepository).deleteById(student.getId());
    }
    @Test
    void willThrowWhenIdNotFound() {
        // Given
        Student student =new Student (1L,"Test","test@gmail.com", LocalDate.now(),0);

        // When
        BDDMockito.given(studentRepository.existsById(student.getId())).willReturn(false);

        // Then
        assertThatThrownBy(() -> underTest.deleteStudent(student.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Student with id: "+ student.getId() + " does not exists");
    }

    @Test
    void updateStudent() {

        // Given

        Student studentObj =new Student (1L,"Test","test@gmail.com", LocalDate.now(),0);
        List<Student> student = new ArrayList<>();
        student.add(studentObj);

        String name = "Vinicius";
        String email = "vini@gmail.com";

        Mockito.when(studentRepository.findStudentById(student.get(0).getId())).thenReturn(student);
        Mockito.when(studentRepository.findById(student.get(0).getId())).thenReturn(Optional.of(studentObj));
        Mockito.when(studentRepository.existsById(student.get(0).getId())).thenReturn(true);

        // when
        underTest.updateStudent(student.get(0).getId(), name, email);

        // then
        assertThat(studentObj.getName()).isEqualTo(name);
        assertThat(studentObj.getEmail()).isEqualTo(email);



    }
}