package fr.ibralogan.mediatheque;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MediathequeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testStringHelloWorld() throws Exception {
        // given : API ready
        // when
        mockMvc.perform(get("/api/todos/string"))
                // then
                .andExpect(content().string("Hello World"));
    }

    @Test
    public void testJsonHelloWorld() throws Exception {
        // given : API ready
        // when
        mockMvc.perform(get("/api/todos/json")
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(jsonPath("$.get").value("Hello World"));
    }

}
