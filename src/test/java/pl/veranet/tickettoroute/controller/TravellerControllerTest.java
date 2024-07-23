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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/testDb.sql")
class TravellerControllerTest {

    @Autowired
    private MockMvc mvc;

    @WithUserDetails("user")
    @Test
    void shouldReturn200AndCreateTraveller() throws Exception {
        mvc.perform(post("/ticket-to-ride/traveller")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name":"Cara",
                            "email":"sara@dot.com"
                        }"""))
                .andExpect(status().isOk());
    }

    @WithUserDetails("user")
    @Test
    void shouldReturn400WhenNameIsNull() throws Exception {
        mvc.perform(post("/ticket-to-ride/traveller")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name":null,
                            "email":"sara@dot.com"
                        }"""))
                .andExpect(status().isBadRequest());
    }

    @WithUserDetails("user")
    @Test
    void shouldReturn400WhenNameIsEmpty() throws Exception {
        mvc.perform(post("/ticket-to-ride/traveller")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name":"",
                            "email":"sara@dot.com"
                        }"""))
                .andExpect(status().isBadRequest());
    }

    @WithUserDetails("user")
    @Test
    void shouldReturn400WhenEmailIsNotValid() throws Exception {
        mvc.perform(post("/ticket-to-ride/traveller")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name":"Sara",
                            "email":"sara.com"
                        }"""))
                .andExpect(status().isBadRequest());
    }

    @WithAnonymousUser
    @Test
    void shouldReturn401WhenAnonymousUserCallCreateTraveller() throws Exception {
        mvc.perform(post("/ticket-to-ride/traveller")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name":"Sara",
                            "email":"sara.com"
                        }"""))
                .andExpect(status().isUnauthorized());
    }

    @WithUserDetails("user")
    @Test
    void shouldReturn200AndDeleteTraveller() throws Exception {
        mvc.perform(delete("/ticket-to-ride/traveller/2"))
                .andExpect(status().isOk());
    }

    @WithAnonymousUser
    @Test
    void shouldReturn401WhenAnonymousUserCallDelete() throws Exception {
        mvc.perform(delete("/ticket-to-ride/traveller/2"))
                .andExpect(status().isUnauthorized());
    }

    @WithUserDetails("admin")
    @Test
    void shouldReturnTravellersWhenMethodCalledByAdmin() throws Exception {
        mvc.perform(get("/ticket-to-ride/admin"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            {
                                "id": 1,
                                "name": "Alex",
                                "email": "nalex@dot.com",
                                "createdDate": "2024-05-05T00:00:00Z",
                                "deletedDate": null
                            },
                            {
                                "id": 2,
                                "name": "Bob",
                                "email": "nob@dot.com",
                                "createdDate": "2024-06-06T00:00:00Z",
                                "deletedDate": null
                            },
                            {
                                "id": 3,
                                "name": "Jack",
                                "email": "nlack@dot.com",
                                "createdDate": "2024-07-07T00:00:00Z",
                                "deletedDate": "2024-06-05T00:00:00Z"
                            }
                        ]"""));
    }

    @WithUserDetails("user")
    @Test
    void shouldReturn403WhenMethodCalledByUser() throws Exception {
        mvc.perform(get("/ticket-to-ride/admin"))
                .andExpect(status().isForbidden());
    }

    @WithAnonymousUser
    @Test
    void shouldReturn401WhenMethodCalledByAnonymousUser() throws Exception {
        mvc.perform(get("/ticket-to-ride/admin"))
                .andExpect(status().isUnauthorized());
    }
}
