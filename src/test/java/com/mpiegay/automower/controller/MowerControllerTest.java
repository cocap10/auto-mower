package com.mpiegay.automower.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MowerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnResult_whenHttpCallingEndpoint_withCorrectInput() throws Exception {
        // GIVEN
        String inputFileContent =
                "5 5    \n" +
                "1 2 N\n" +
                "LFLFLFLFF    \n" +
                "3 3 E\n" +
                "FFRFFRFRRF";

        // WHEN - THEN
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/mower/runFromInput")
                .content(inputFileContent))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        assertThat(result)
                .contains(
                        "1 3 N\n" +
                        "5 1 E\n");
    }

    @Test
    void shouldReturnErrorMessage_whenHttpCallingEndpoint_withIncorrectInputFile() throws Exception {
        // GIVEN
        String inputFileContent = "invalid file";

        // WHEN - THEN
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/mower/runFromInput")
                .content(inputFileContent))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse().getContentAsString();
        assertThat(result)
                .isEqualTo("Invalid input file. The file must contain first the lawn dimention and at least one mower position and instructions sequence");
    }
}