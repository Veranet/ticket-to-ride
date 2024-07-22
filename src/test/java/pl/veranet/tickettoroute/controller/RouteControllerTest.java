package pl.veranet.tickettoroute.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import pl.veranet.tickettoroute.entity.Route;
import pl.veranet.tickettoroute.repository.RouteRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RouteControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    RouteRepository routeRepository;

    @Test
    void shouldAuthRequestSuccessfullyWith200() throws Exception {
        List<Route> segments = new ArrayList<>();
        segments.add(new Route("benc", "cov", 1));
        segments.add(new Route("benc", "bris", 3));
        segments.add(new Route("benc", "swip", 4));
        segments.add(new Route("cov", "n", 2));
        segments.add(new Route("n", "london", 2));
        segments.add(new Route("bris", "swip", 2));
        segments.add(new Route("swip", "r", 4));
        segments.add(new Route("r", "london", 1));
        when(routeRepository.findAll()).thenReturn(segments);

        mvc.perform(get("/price")
                        .param("fromTown", "cov")
                        .param("toTown", "r"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"segments\":5,\"price\":17.0,\"currency\":\"GBP\"}"));
    }

    @Test
    void shouldRequestWith403WhenFromAndToAreTheSame() throws Exception {
        mvc.perform(get("/price").param("fromTown", "r")
                .param("toTown", "r"))
                .andExpect(status().isForbidden());
    }
}
