package com.prueba;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.controller.CategoryController;
import com.prueba.model.Category;
import com.prueba.service.CategoryService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(value = CategoryController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryControllerTest {

    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2ODQ1OTM4NzYsInN1YiI6InMucm9qYXNoQHNlbmEuZWR1LmNvIiwibmJmIjoxNjg0NTkzODc2LCJleHAiOjE4NDIyNzM4NzZ9.4yWZTtGjmIpNVXi7ZMYJE3gb_fYnj9JZKWll-ac4l8M";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    ObjectMapper objectMapper;

    @BeforeEach
    void config() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName(value = "Test Controller - Ping category")
    @Order(1)
    void testPing() throws Exception {
        mockMvc.perform(get("/Category/ping")
                        .header("Authorization", "Bearer "+ token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "Test Controller - Get categories")
    @Order(2)
    void getCategory() throws Exception {
        Category category = new Category(1L,"SERVIDORES","DESCIPCIÓN","PINTURE");

        List<Category> categoryList = Arrays.asList(category);

        when(categoryService.getAll()).thenReturn(categoryList);

        mockMvc.perform(get("/Categories/")
                        .header("Authorization", "Bearer "+ token))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).getAll();
    }

    @Test
    @DisplayName(value = "Test Controller - Post category")
    @Order(3)
    void postCategory() throws Exception {
        Category category = new Category(1L,"SERVIDORES","DESCIPCIÓN","PINTURE");

        when(categoryService.save(any())).then(invocation -> {
            Category c = invocation.getArgument(0);
            c.setCategoryId(1L);
            return c;
        });

        mockMvc.perform(post("/Category/")
                        .header("Authorization", "Bearer "+ token)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.categoryId").value(1L))
                .andExpect(jsonPath("$.categoryName").value("SERVIDORES"))
                .andExpect(jsonPath("$.description").value("DESCIPCIÓN"))
                .andExpect(jsonPath("$.picture").value("PINTURE"));

        verify(categoryService).save(any());
    }
}
