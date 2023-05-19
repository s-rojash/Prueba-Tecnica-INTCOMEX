package com.prueba;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.controller.CategoryController;
import com.prueba.controller.ProductController;
import com.prueba.model.Category;
import com.prueba.model.Product;
import com.prueba.service.CategoryService;
import com.prueba.service.ProductService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private CategoryService categoryService;

    ObjectMapper objectMapper;

    @BeforeEach
    void config() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName(value = "Test Controller - Ping product")
    @Order(1)
    void testPing() throws Exception {
        mockMvc.perform(get("/Product/ping").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "Test Controller - Get products")
    @Order(2)
    void getProduct() throws Exception {
        Category category = new Category(1L,"SERVIDORES","DESCIPCIÓN","PINTURE");
        Product product = new Product(1L, "PRODUCTO",1L, category, 1,1,1,1,1,true);

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Page<Product> products = new PageImpl<>(productList);
        Pageable pageable = PageRequest.of(0,1);

        when(productService.getAll(pageable)).thenReturn(products);

        mockMvc.perform(get("/Products/"))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName(value = "Test Controller - Post product")
    @Order(3)
    void postProduct() throws Exception {
        Category category = new Category(1L,"SERVIDORES","DESCIPCIÓN","PINTURE");

        List<Category> categoryList = Arrays.asList(category);

        Product product = new Product(1L, "PRODUCTO",1L, category, 1,1,1,1,1,true);


        when(categoryService.save(any())).then(invocation -> {
            Category c = invocation.getArgument(0);
            c.setCategoryId(1L);
            return c;
        });

        mockMvc.perform(post("/Product/").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)));

    }
}
