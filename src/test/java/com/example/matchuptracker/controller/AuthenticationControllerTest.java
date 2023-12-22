package com.example.matchuptracker.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;


//    @Test
//    @DisplayName("Test handleGoogleLoginSuccess with authority 'read'")
//    public void testHandleGoogleLoginSuccess() throws Exception {
//        // Create authorities
//        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("read"));
//
//        // Set up attributes
//        Map<String, Object> attributes = Collections.singletonMap("email", "email@example.com");
//
//        // Create OAuth2User
//        OAuth2User principal = new DefaultOAuth2User(authorities, attributes, "email");
//
//        String expectedToken = "mockJwtToken";
//        // Assuming JwtUtil.createJwtToken(...) returns expectedToken in your controller
//
//        ResultActions response = mockMvc.perform(get("/login/success")
//                        .with(oauth2Login().oauth2User(principal)))
//                .andExpect(status().isOk())
//                .andExpect(content().string(expectedToken));
//    }
}

//    @Test
//    @DisplayName("Test handleGoogleLoginSuccess without authority 'read'")
//    @WithMockUser(authorities = {"none"})
//    public void testHandleGoogleLoginFailure() throws Exception {
//        mockMvc.perform(get("/login/success"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(AuthenticationController.ERROR_MESSAGE));
//    }

