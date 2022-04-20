package com.hatchways.finance.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hatchways.finance.model.User;
import com.hatchways.finance.schema.AuthRequestBody;
import com.hatchways.finance.schema.AuthResponse;
import com.hatchways.finance.serialization.LocalDateTimeSerializer;
import com.hatchways.finance.service.UserService;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@TestPropertySource(properties = {"SESSION_SECRET=testsecret"})
public class AuthControllerTest {

  @Autowired Environment env;

  @Autowired MockMvc mockMvc;

  @MockBean private UserService userService;

  @Test
  public void testSignupOk() throws Exception {
    User user = TestUtil.getUser();

    AuthRequestBody body = new AuthRequestBody();
    body.setEmail(user.getEmail());
    body.setPassword("sample");

    when(userService.getByEmail(user.getEmail())).thenReturn(null);
    when(userService.createUser(user.getEmail(), body.getPassword())).thenReturn(user);

    Gson gson =
        new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .create();

    MvcResult result =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(body)))
            .andExpect(status().isOk())
            .andReturn();

    AuthResponse response =
        gson.fromJson(result.getResponse().getContentAsString(), AuthResponse.class);
    assertFalse(response.getToken().isEmpty());
    assertEquals(user.getId(), response.getUser().getId());
    assertEquals(user.getEmail(), response.getUser().getEmail());
    assertEquals(user.getCreatedAt(), response.getUser().getCreatedAt());
    assertEquals(user.getUpdatedAt(), response.getUser().getUpdatedAt());

    verify(userService, times(1)).getByEmail(body.getEmail());
    verify(userService, times(1)).createUser(body.getEmail(), body.getPassword());
  }

  @Test
  public void testSignupAlreadyExists() throws Exception {
    AuthRequestBody body = new AuthRequestBody();
    body.setEmail("test@test.com");
    body.setPassword("sample");

    when(userService.getByEmail(body.getEmail())).thenReturn(new User());

    Gson gson =
        new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .create();

    MvcResult result =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(body)))
            .andExpect(status().is(400))
            .andReturn();

    assertEquals("Email already registered", result.getResponse().getContentAsString());
  }
}
