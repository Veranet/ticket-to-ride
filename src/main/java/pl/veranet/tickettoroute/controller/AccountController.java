package pl.veranet.tickettoroute.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.veranet.tickettoroute.dto.AccountBalanceDto;
import pl.veranet.tickettoroute.service.AccountService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/ticket-to-ride/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PatchMapping
    public void refillBalance(@RequestParam("id") int id,
                              @RequestParam("amount") BigDecimal amount) {
        accountService.updateBalance(id, amount);
    }

    @GetMapping
    public AccountBalanceDto getBalance(@RequestParam("id") int id) {
        return new AccountBalanceDto(accountService.getBalance(id));
    }
}