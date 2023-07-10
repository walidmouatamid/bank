package com.societegenerale.bank.commande_side.controllers;

import com.societegenerale.bank.commande_side.services.IAccountCommandService;
import com.societegenerale.bank.core_api.dtos.CreateAccountRequestDto;
import com.societegenerale.bank.core_api.dtos.CreditAccountRequestDto;
import com.societegenerale.bank.core_api.dtos.DebitAccountRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/api/v1/command/account")
@AllArgsConstructor
public class AccountCommandController {
    private final IAccountCommandService accountCommandService;
    @PutMapping
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDto request){
        return accountCommandService.createAccount(request);
    }

    @PostMapping(path = "/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDto debitAccountRequestDto){
        return accountCommandService.debitAccount(debitAccountRequestDto);
    }

    @PostMapping(path = "/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDto creditAccountRequestDto){
        return accountCommandService.creditAccount(creditAccountRequestDto);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> exceptionHandler(RuntimeException exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
