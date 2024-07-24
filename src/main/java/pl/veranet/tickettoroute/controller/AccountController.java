package pl.veranet.tickettoroute.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.veranet.tickettoroute.dto.AccountBalanceDto;
import pl.veranet.tickettoroute.dto.BalanceDto;
import pl.veranet.tickettoroute.service.AccountService;

@RestController
@RequestMapping("/ticket-to-ride/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PatchMapping
    public void refillBalance(@RequestBody BalanceDto balanceDto) {
        accountService.updateBalance(balanceDto.getAccountId(), balanceDto.getAmount());
    }

    @GetMapping
    public AccountBalanceDto getBalance(@RequestParam("id") int id) {
        return new AccountBalanceDto(accountService.getBalance(id));
    }
}
