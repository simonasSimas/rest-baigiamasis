package lt.codeacademy.restbagiamasis.controller;

import lt.codeacademy.restbagiamasis.dto.request.create.CreateFormRequest;
import lt.codeacademy.restbagiamasis.dto.response.create.CreateFormResponse;
import lt.codeacademy.restbagiamasis.entity.question.Question;
import lt.codeacademy.restbagiamasis.enums.Error;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.codeacademy.restbagiamasis.dto.response.ErrorResponse;
import lt.codeacademy.restbagiamasis.entity.Form;
import lt.codeacademy.restbagiamasis.repository.FormRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureMockMvc
@WithUserDetails("ad")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FormControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetFormWhenFormExists() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/forms/{id}", 2L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Form form = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Form.class);

        assertEquals(2L, form.getId());
    }

    @Test
    public void testGetCarWhenCarDoesNotExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/forms/{id}", 3L))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        ErrorResponse errorResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);

        assertEquals(404, errorResponse.getStatus());
        assertEquals(Error.RESOURCE_NOT_FOUND, errorResponse.getCode());
        assertEquals("Resource with id = 3 was not found", errorResponse.getMessage());
    }

    @Test
    public void testCreateForm() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        CreateFormRequest createFormRequest = new CreateFormRequest(
                1L,
                "Test",
                "test",
                23,
                formRepository.getById(1L).getQuestions(),
                1
        );

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/forms/create")
                        .content(objectMapper.writeValueAsString(createFormRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        CreateFormResponse createFormResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CreateFormResponse.class);

        Form form = formRepository.findById(createFormResponse.getId()).get();

        assertEquals(createFormRequest.getFormTitle(), form.getFormTitle());
        assertEquals(createFormRequest.getFormDescription(), form.getFormDescription());
        assertEquals(createFormRequest.getMaximumScore(), form.getMaximumScore());
        assertEquals(createFormRequest.getQuestions(), form.getQuestions());
        assertEquals(createFormRequest.getNumberOfQuestions(), form.getNumberOfQuestions());

        assertNotNull(form.getCreated());
        assertNotNull(form.getUpdated());
        assertTrue(form.getCreated().isAfter(now));
        assertTrue(form.getUpdated().isAfter(now));

        assertEquals(form.getFormTitle(), createFormResponse.getFormTitle());
        assertEquals(form.getFormDescription(), createFormResponse.getFormDescription());
        assertEquals(form.getQuestions(), createFormResponse.getQuestions());
    }

    @Test
    public void testCreateCarWithInvalidBody() throws Exception {

        CreateFormRequest createFormRequest = new CreateFormRequest(
                1L,
                "test",
                "test",
                23,
                new HashSet<Question>(),
                1
        );

        int size = formRepository.findAll().size();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/forms/create")
                        .content(objectMapper.writeValueAsString(createFormRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        ErrorResponse errorResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);

        assertEquals(400, errorResponse.getStatus());
        assertEquals(Error.VALIDATION_FAILED, errorResponse.getCode());
        assertEquals("Invalid request body provided", errorResponse.getMessage());
        assertEquals(size, formRepository.findAll().size());
    }

    @Test
    public void testDeleteFormWhenFormExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/forms/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        assertFalse(formRepository.existsById(1L));
    }
}
