package mumm.weatherstation.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class StationManagerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldCreateStation() throws Exception {
        String body = """
                {
                  "name": "Tallinn",
                  "latitude": 59.437,
                  "longitude": 24.7536
                }
                """;

        String response = mockMvc.perform(post("/stations")
                        .with(httpBasic("test", "test"))
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.name").value("Tallinn"))
                .andExpect(jsonPath("$.data.latitude").value(59.437))
                .andExpect(jsonPath("$.data.longitude").value(24.7536))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode json = objectMapper.readTree(response);
        long id = json.path("data").path("id").asLong();

        assertTrue(id > 0);
    }

    @Test
    void shouldReturnBadRequestForInvalidLatitude() throws Exception {
        String body = """
                {
                  "name": "Tallinn",
                  "latitude": 200,
                  "longitude": 24.7536
                }
                """;

        mockMvc.perform(post("/stations")
                        .with(httpBasic("test", "test"))
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());
    }

    @Test
    void shouldGetStationById() throws Exception {
        String createBody = """
                {
                  "name": "Tartu",
                  "latitude": 58.3776,
                  "longitude": 26.7290
                }
                """;

        String response = mockMvc.perform(post("/stations")
                        .with(httpBasic("test", "test"))
                        .contentType("application/json")
                        .content(createBody))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode json = objectMapper.readTree(response);
        long id = json.path("data").path("id").asLong();

        mockMvc.perform(get("/stations/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.name").value("Tartu"))
                .andExpect(jsonPath("$.data.latitude").value(58.3776))
                .andExpect(jsonPath("$.data.longitude").value(26.7290));
    }

    @Test
    void shouldDeleteStation() throws Exception {
        String createBody = """
                {
                  "name": "Delete me",
                  "latitude": 59.437,
                  "longitude": 24.7536
                }
                """;

        String response = mockMvc.perform(post("/stations")
                        .with(httpBasic("test", "test"))
                        .contentType("application/json")
                        .content(createBody))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode json = objectMapper.readTree(response);
        long id = json.path("data").path("id").asLong();

        mockMvc.perform(delete("/stations/" + id)
                        .with(httpBasic("test", "test")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/stations/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnUnauthorizedWithoutAuth() throws Exception {
        String body = """
                {
                  "name": "Tallinn",
                  "latitude": 59.437,
                  "longitude": 24.7536
                }
                """;

        mockMvc.perform(post("/stations")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isUnauthorized());
    }
}