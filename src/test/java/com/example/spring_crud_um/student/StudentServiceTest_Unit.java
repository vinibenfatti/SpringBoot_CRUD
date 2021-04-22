package com.example.spring_crud_um.student;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
    @Disabled
    void canGetStudentsById() {
        //Given
        Long id = 1L;

        //When
        underTest.getStudentsById(id);

        //Then
        ArgumentCaptor<Long> LongArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(studentRepository).findStudentById(LongArgumentCaptor.capture());

        Long capturedStudent = LongArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(id);

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
    @Disabled
    void deleteStudent() {
    }

    @Test
    @Disabled
    void updateStudent() {
    }
}