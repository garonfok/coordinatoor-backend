package com.coordinatoor.backend;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.coordinatoor.backend.entity.Profile;
import com.coordinatoor.backend.repository.ProfileRepository;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ProfileRepository profileRepository;

  @BeforeAll
  public void setup() {
    profileRepository.save(new Profile("johnsmith", "johnsmith@email.com"));
  }

  @Test
  public void testProfile() throws Exception {
    mockMvc.perform(get("/profile/1"))
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$.username").value("johnsmith"))
        .andExpect(jsonPath("$.email").value("johnsmith@email.com"));
    mockMvc.perform(get("/profile/email/johnsmith@email.com"))
        .andExpect(status().isOk()).andExpect(
            jsonPath("$.username").value("johnsmith"))
        .andExpect(jsonPath("$.email").value("johnsmith@email.com"));
    ;

    mockMvc.perform(get("/profile/search/email/johnsmith"))
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$[0].username").value("johnsmith"))
        .andExpect(jsonPath("$[0].email").value("johnsmith@email.com"));

    mockMvc.perform(get("/profile/search/username/john"))
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$[0].username").value("johnsmith"))
        .andExpect(jsonPath("$[0].email").value("johnsmith@email.com"));

    mockMvc.perform(post("/profile/")
        .contentType("application/json")
        .content("{\"username\":\"johndoe\",\"email\":\"johndoe@email.com"
            + "\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("johndoe"))
        .andExpect(jsonPath("$.email").value("johndoe@email.com"));

    mockMvc.perform(get("/profile/2"))
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$.username").value("johndoe"))
        .andExpect(jsonPath("$.email").value("johndoe@email.com"));

    mockMvc.perform(put("/profile/2")
        .contentType("application/json")
        .content("{\"username\":\"janedoe\",\"email\":\"janedoe@email.com"
            + "\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("janedoe"))
        .andExpect(jsonPath("$.email").value("janedoe@email.com"));

    mockMvc.perform(delete("/profile/2"))
        .andExpect(status().isOk());

    assertThrows(
        Exception.class,
        () -> mockMvc.perform(get("/profile/2"))
            .andExpect(status().isNotFound()));
  }
}
