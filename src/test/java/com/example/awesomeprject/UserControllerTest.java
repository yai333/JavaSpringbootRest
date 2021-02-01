package com.example.awesomeprject;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import org.apache.log4j.Logger;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;	
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    static Logger log = Logger.getLogger(UserControllerTest.class.getName());
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findAll() throws Exception {
        log.info("Should return all users");
        userRepository.save(new User(1, "yi", "ai"));

        final MvcResult result = mvc.perform(get("/users/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName", is("yi"))).andExpect(status().isOk()).andReturn();

        log.info(result.getResponse().getContentAsString());
    }

    @Test
    public void addUser() throws Exception {
        log.info("Should add a new user");

        String exampleUserJson = "{\"firstName\": \"tom\", \"lastName\": \"cat\"}";
        mvc.perform(post("/user")
                .accept(MediaType.APPLICATION_JSON).content(exampleUserJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<User> users = userRepository.findByLastName("cat");
        assertEquals(users.get(0).getFirstName(), "tom");
    }
}
