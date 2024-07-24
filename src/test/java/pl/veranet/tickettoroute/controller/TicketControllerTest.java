package pl.veranet.tickettoroute.controller;

import org.junit.jupiter.api.Test;
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
    @Test
    void shouldReturnBadRequestWhenFromIsNull() throws Exception {
        mvc.perform(post("/ticket-to-ride/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "fromTown": null,
                                    "toTown": "B",
                                    "travellerId": 1,
                                    "price": 7
                                }"""))
                .andExpect(status().isBadRequest());
    }

    @WithUserDetails("user")
    @Test
    void shouldReturnBadRequestWhenFromIsEmpty() throws Exception {
        mvc.perform(post("/ticket-to-ride/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "fromTown": "",
                                    "toTown": "B",
                                    "travellerId": 1,
                                    "price": 7
                                }"""))
                .andExpect(status().isBadRequest());
    }

    @WithUserDetails("user")
    @Test
    void shouldReturnBadRequestWhenToIsEmpty() throws Exception {
        mvc.perform(post("/ticket-to-ride/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "fromTown": "A",
                                    "toTown": "",
                                    "travellerId": 1,
                                    "price": 7
                                }"""))
                .andExpect(status().isBadRequest());
    }

    @WithUserDetails("user")
    @Test
    void shouldReturnBadRequestWhenToIsNull() throws Exception {
        mvc.perform(post("/ticket-to-ride/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "fromTown": "A",
                                    "toTown": null,
                                    "travellerId": 1,
                                    "price": 7
                                }"""))
                .andExpect(status().isBadRequest());
    }

    @WithUserDetails("user")
    @Test
    void shouldReturnBadRequestWhenPriceIsNegative() throws Exception {
        mvc.perform(post("/ticket-to-ride/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "fromTown": "A",
                                    "toTown": null,
                                    "travellerId": 1,
                                    "price": -7
                                }"""))
                .andExpect(status().isBadRequest());
    }

    @WithUserDetails("user")
    @Test
    void shouldReturnBadRequestWhenPrice0() throws Exception {
        mvc.perform(post("/ticket-to-ride/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "fromTown": "A",
                                    "toTown": null,
                                    "travellerId": 1,
                                    "price": 0
                                }"""))
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
