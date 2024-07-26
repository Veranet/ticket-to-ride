package pl.veranet.tickettoroute.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/testDb.sql")
class TicketControllerTest {

    @Autowired
    private MockMvc mvc;

    @WithUserDetails("user")
    @Test
    void shouldReturnLackOfFundsResponseRealDB() throws Exception {
        mvc.perform(post("/ticket-to-ride/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "fromTown": "A",
                                    "toTown": "B",
                                    "travellerId": 2,
                                    "price": 7
                                }"""))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"result\":\"lackOf\",\"currency\":\"GBP\",\"lackOf\":\"7.0\"}"));
    }

    @WithUserDetails("user")
    @Test
    void shouldReturnSuccessResponseRealDB() throws Exception {
        mvc.perform(post("/ticket-to-ride/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "fromTown": "A",
                                    "toTown": "B",
                                    "travellerId": 1,
                                    "price": 7
                                }"""))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"result\":\"success\",\"currency\":\"GBP\",\"change\":\"18.0\"}"));
    }

    @WithUserDetails("user")
    @ParameterizedTest
    @CsvSource(textBlock = """
           # fromTown           toTown
               null,           null
               "A",            null
               null,           "B"
               "",             "B"
               "   ",          "B"
               "A",            ""
               "A",            "   "
               "",             ""
               "   ",          "  "
           """, nullValues = "null")
    void shouldReturnBadRequestWhenInvalidParameters(String fromTown, String toTown) throws Exception {
        String requestBody = String.format("""
                {
                    "fromTown": %s,
                    "toTown": %s,
                    "travellerId": 1,
                    "price": 7
                }""", fromTown, toTown);
        mvc.perform(post("/ticket-to-ride/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @WithUserDetails("user")
    @ParameterizedTest
    @ValueSource(doubles = {-7, 0})
    void shouldReturnBadRequestWhenPriceIsNegativeOrZero(double invalidPrice) throws Exception {
        String requestBody = String.format("""
                {
                    "fromTown": "A",
                    "toTown": "B",
                    "travellerId": 1,
                    "price": %s
                }""", invalidPrice);
        mvc.perform(post("/ticket-to-ride/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @WithAnonymousUser
    @Test
    void shouldReturnUnauthorizedWhenAnonymousUse() throws Exception {
        mvc.perform(post("/ticket-to-ride/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "fromTown": "A",
                                    "toTown": null,
                                    "travellerId": 1,
                                    "price": 0
                                }"""))
                .andExpect(status().isUnauthorized());
    }
}
