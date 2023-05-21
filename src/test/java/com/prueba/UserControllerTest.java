package com.prueba;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.controller.ProductController;
import com.prueba.controller.UserController;
import com.prueba.model.User;
import com.prueba.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(value = UserController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    ObjectMapper objectMapper;
    @BeforeEach
    void config() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName(value = "Test Controller - Create user")
    @Order(1)
    void saveUser() throws Exception {
        User user = new User(1L,"s.rojash@uniandes.edu.co","1234");
        when(userService.save(any())).then(invocation -> {
            User u = invocation.getArgument(0);
            u.setUserId(1L);
            return u;
        });
        mockMvc.perform(post("/User/").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.email").value("s.rojash@uniandes.edu.co"));
        verify(userService).save(any());
    }
}
