//package com.example.controllers;
//
//import com.example.SecureChat.SecureChatApplication;
//import com.example.model.Message;
//import com.example.repository.MessageRepository;
//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.test.context.ActiveProfiles;
//
//import static io.restassured.RestAssured.*;
//import static org.hamcrest.Matchers.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SecureChatApplication.class)
//@ActiveProfiles("test")
////@Import(ChatControllerIntegrationTest.TestSecurityConfig.class)
//public class ChatControllerIntegrationTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private MessageRepository messageRepository;
//
//    @BeforeEach
//    public void setUp() {
//        RestAssured.port = port;
//        messageRepository.deleteAll();
//    }
//
//    @Test
//    void testSendAndRetrieveMessage() {
//        Message message = new Message();
//        message.setContent("Hello, this is a test message!");
//
//        given()
//                .auth().basic("user", "password")
//                .contentType(ContentType.JSON)
//                .body(message)
//                .when()
//                .post("/api/chat/send")
//                .then()
//                .statusCode(200)
//                .body("content", equalTo(message.getContent()))
//                .body("encryptedAesKey", notNullValue());
//
//        Message[] messages = given()
//                .auth().basic("user", "password")
//                .when()
//                .get("/api/chat/messages")
//                .then()
//                .statusCode(200)
//                .extract().as(Message[].class);
//
//        assertEquals(1, messages.length);
//        assertEquals("Hello, this is a test message!", messages[0].getContent());
//    }
////    @TestConfiguration
////    static class TestSecurityConfig {
////        @Bean
////        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////            http.csrf().disable()
////                    .authorizeRequests().anyRequest().authenticated()
////                    .and()
////                    .httpBasic();
////            return http.build();
////        }
////
////        @Bean
////        public UserDetailsService userDetailsService() {
////            UserDetails user = User.withDefaultPasswordEncoder()
////                    .username("user")
////                    .password("password")
////                    .roles("USER")
////                    .build();
////            return new InMemoryUserDetailsManager(user);
////        }
////    }
//}

package com.example.controllers;

import com.example.SecureChat.SecureChatApplication;
import com.example.model.Message;
import com.example.repository.MessageRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SecureChatApplication.class)
@ActiveProfiles("test")
@Import(ChatControllerIntegrationTest.TestSecurityConfig.class)  // Make sure the security config is imported
public class ChatControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        messageRepository.deleteAll();
    }

    @Test
    void testSendAndRetrieveMessage() {
        Message message = new Message();
        message.setContent("Hello, this is a test message!");

        // Sending a message
        given()
                .auth().basic("user", "password")  // Using basic authentication
                .contentType(ContentType.JSON)
                .body(message)
                .when()
                .post("/api/chat/send")
                .then()
                .statusCode(200)
                .body("content", equalTo(message.getContent()))
                .body("encryptedAesKey", notNullValue());

        // Retrieving the message
        Message[] messages = given()
                .auth().basic("user", "password")
                .when()
                .get("/api/chat/messages")
                .then()
                .statusCode(200)
                .extract().as(Message[].class);

        assertEquals(1, messages.length);
        assertEquals("Hello, this is a test message!", messages[0].getContent());
    }
    @TestConfiguration
    static class TestSecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable())  // Disable CSRF for testing purposes
                    .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())  // Require authentication for all requests
                    .httpBasic(basic -> {});  // Enable HTTP Basic authentication using a lambda
            return http.build();
        }

        @Bean
        public UserDetailsService userDetailsService() {
            // In-memory user details for testing authentication
            UserDetails user = User.withDefaultPasswordEncoder()
                    .username("user")
                    .password("password")
                    .roles("USER")
                    .build();
            return new InMemoryUserDetailsManager(user);
        }
    }



}


