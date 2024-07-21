package pl.veranet.tickettoroute.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.veranet.tickettoroute.service.AccountService;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accountService;

    @Test
    void refillBalance() throws Exception {
        doNothing().when(accountService).updateBalance(anyInt(), any(BigDecimal.class));

        mvc.perform(patch("/ticket-to-ride/account")
                        .param("id", "1")
                        .param("amount", "100.00"))
                .andExpect(status().isOk());
    }

    @Test
    void getBalance() throws Exception {
        when(accountService.getBalance(anyInt())).thenReturn(100.00);

        mvc.perform(get("/ticket-to-ride/account")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"accountBalance\":100.00}"));
    }
}