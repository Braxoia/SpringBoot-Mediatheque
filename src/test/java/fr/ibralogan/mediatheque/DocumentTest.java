package fr.ibralogan.mediatheque;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static fr.ibralogan.mediatheque.security.SecurityConstants.SECRET;
import static fr.ibralogan.mediatheque.security.SecurityConstants.TOKEN_PREFIX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class DocumentTest {
    @Autowired
    private MockMvc mockMvc;

    /** Password used for accounts created during the test suite */
    private static final String librarian = "Bibliothequaire";
    /** Account that is living only while the class test is running (deleted before each test) */
    private static final String subscriber = "Abonne";

    // -- DEBUT DES TESTS ---------------------------------------------------
    private String getTokenOf(String type) throws Exception {
        type = type.equals("librarian") ? librarian : subscriber;
        mockMvc.perform(
                        post("/api/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{" +
                                        "\"username\": \"documentunit-"+type+"\"," +
                                        "\"password\": \"unit\"," +
                                        "\"type\": \""+type+"\"" +
                                        "}")
                );
        MvcResult result = mockMvc.perform(
                        post("/api/auth/signin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{" +
                                        "\"username\": \"documentunit-"+type+"\"," +
                                        "\"password\": \"unit\"," +
                                        "\"type\": \""+type+"\"" +
                                        "}")
                )
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();
        String actualUsername = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
        return token;
    }


    @Test
    @Order(1)
    public void givenLibrarianAccountAndValidDocument_whenAddDocument_thenReturnCreatedResponse() throws Exception {
        String token = this.getTokenOf("librarian");
        mockMvc.perform(
                        post("/api/documents/addDocument")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token)
                                .content("{" +
                                        "\"titre\": \"Le temps des templates\"," +
                                        "\"typeDocument\": 1," +
                                        "\"reserveur\": 1" +
                                        "}")
                )
                .andExpect(status().isCreated());
    }
}
