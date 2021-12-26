package app.fsdev.dev1stest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class Dev1stestApplicationTest {

    private MockMvc mockMvc;

    @Autowired
    private ItemController itemController;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    void get_test() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/item"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(333))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemName")
                        .value("Mr. Yamamoto"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.price").value(777));
    }
}