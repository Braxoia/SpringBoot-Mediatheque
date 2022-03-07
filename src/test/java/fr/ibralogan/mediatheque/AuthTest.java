package fr.ibralogan.mediatheque;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.junit.jupiter.api.*;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class AuthTest {
    @Autowired
    private MockMvc mockMvc;

    /** Password used for accounts created during the test suite */
    private static final String password = "test";
    /** Account that is living only while the class test is running (deleted before each test) */
    private static final String username = "unittest";

    // -- DEBUT DES TESTS ---------------------------------------------------

    @Test
    @Order(1)
    public void onSignUp_withNewUser_returnStatus201() throws Exception {
        mockMvc.perform(
                        post("/api/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{" +
                                        "\"username\":\"" + username + "\"," +
                                        "\"password\":\"" + password + "\"," +
                                        "\"type\": \"Bibliothequaire\"" +
                                        "}")
                )
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void onSignUp_withExistingUser_returnStatus412() throws Exception {
        mockMvc.perform(
                        post("/api/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{" +
                                        "\"username\":\"" + username + "\"," +
                                        "\"password\":\"" + password + "\"," +
                                        "\"type\": \"Bibliothequaire\"" +
                                        "}")
                )
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void onSignIn_withWrongCredentials_returnStatus401() throws Exception {
        mockMvc.perform(
                        post("/api/auth/signin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"username\":\"" + username + "\",\"password\":\"" + password + "is wrong" + "\"}")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void onSignIn_withUnknownUsername_returnStatus404() throws Exception {
        mockMvc.perform(
                        post("/api/auth/signin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"username\":\"" + username + "isWrong" + "\",\"password\":\"" + password + "\"}")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void onSignIn_withCorrectCredentials_getUserIdAndToken() throws Exception {
        MvcResult result = mockMvc.perform(
                        post("/api/auth/signin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}")
                )
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();
        String actualUsername = "";
        try {
            actualUsername = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
        } catch (JWTVerificationException e) {
            fail("Unable to decode token : " + e.getMessage());
        }
        assertEquals(AuthTest.username, actualUsername, "Test username should be the equal to the one stored in the token");
    }
}
