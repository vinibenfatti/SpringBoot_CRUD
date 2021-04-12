package com.example.spring_crud_um;


import org.junit.Before;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE )


class StudentControllerTest {

    @Autowired
    private MockMvc mvc;


    @Autowired
    HttpSecurity http;

    @Autowired
    private WebApplicationContext context;


    // @Before
    //public void setUp() {
    //mvc = MockMvcBuilders
    //.webAppContextSetup(context)
    //.apply(springSecurity())
    //.build();
    //}


    @Test
    void getStudentsTest() throws Exception {
        //Given
        URI uri = new URI("/api/v1/student");

        //Authentication a = SecurityContextHolder.getContext().getAuthentication();

        //List<Student> studentList= controller.getStudents();

        //Assert.assertNotNull(studentList);

        //When                                          //Then
        //mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().is(200));

        this.mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    //@WithMockUser("spring")
    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        //mvc.perform(get("/private/hello")
        //.contentType(MediaType.APPLICATION_JSON))
        //.andExpect(status().isOk());
    }

}



