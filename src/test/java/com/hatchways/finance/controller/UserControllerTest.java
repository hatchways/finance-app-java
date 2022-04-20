package com.hatchways.finance.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hatchways.finance.model.User;
import com.hatchways.finance.serialization.LocalDateTimeSerializer;
import com.hatchways.finance.service.UserService;
import com.hatchways.finance.util.AuthUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@TestPropertySource(properties = {"SESSION_SECRET=testsecret"})
public class UserControllerTest {

  @Autowired Environment env;

  @Autowired MockMvc mockMvc;

  @MockBean private UserService userService;

  @Test
  public void testGetAuthenticatedUser() throws Exception {
    User user = TestUtil.getUser();

    String token = AuthUtil.generateToken(user.getEmail(), env.getProperty("SESSION_SECRET"));

    when(userService.getByEmail(user.getEmail())).thenReturn(user);

    MvcResult result =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get("/api/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", String.format("Bearer %s", token)))
            .andExpect(status().isOk())
            .andReturn();

    Gson gson =
        new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .create();
    User respUser = gson.fromJson(result.getResponse().getContentAsString(), User.class);
    assertEquals(user.getId(), respUser.getId());
    assertEquals(user.getEmail(), respUser.getEmail());
    assertEquals(user.getCreatedAt(), respUser.getCreatedAt());
    assertEquals(user.getUpdatedAt(), respUser.getUpdatedAt());

    verify(userService, times(1)).getByEmail(user.getEmail());
  }
}
