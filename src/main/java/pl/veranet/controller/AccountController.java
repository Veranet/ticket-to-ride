package pl.veranet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.veranet.service.AccountService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/ticket-to-ride/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PatchMapping
    public void refillBalance(@RequestParam Integer id,
                              @RequestParam BigDecimal amount) {
        accountService.updateBalance(id, amount);
    }

    @GetMapping
    public int showBalance(@RequestParam Integer id) {
        return accountService.getBalance(id);
    }
}
