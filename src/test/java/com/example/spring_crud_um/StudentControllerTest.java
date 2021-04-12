package com.example.spring_crud_um;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;


@RunWith(SpringRunner.class)
@SpringBootTest //@SpringBootTest(classes = Main, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WebMvcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE )


class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //@LocalServerPort
    //int port

    //def request = createRequest().header(new Header("traceId", traceId)).auth().preemptive().basic(user, password)


    @Autowired
    HttpSecurity http;


   // @Autowired
   // private StudentController studentController;

    //@Before
   /* public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }*/

    @Test
    void getStudentsTest() throws Exception {
        //Given
        URI uri = new URI("/api/v1/student");

        //Authentication a = SecurityContextHolder.getContext().getAuthentication();

        //List<Student> studentList= controller.getStudents();

        //Assert.assertNotNull(studentList);

        //When                                          //Then
        //mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().is(200));

        this.mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk());
    }

}



