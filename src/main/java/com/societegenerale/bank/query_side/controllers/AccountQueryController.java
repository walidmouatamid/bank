package com.societegenerale.bank.query_side.controllers;

import com.societegenerale.bank.core_api.dtos.*;
import com.societegenerale.bank.query_side.services.IAccountQueryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/query/account")
@AllArgsConstructor
public class AccountQueryController {

    private IAccountQueryService accountService;
    @GetMapping
    public List<AccountResponseDto> getAllAccounts(){
        GetAllAccountsQueryDto query = new GetAllAccountsQueryDto();
        return accountService.handle(query);
    }

    @GetMapping(path = "/{id}")
    public AccountResponseDto getAccountById(@PathVariable String id){
        GetAccountQueryDto query = new GetAccountQueryDto(id);
        return accountService.handle(query);
    }

    @GetMapping(path = "/{id}/operations")
    public List<OperationResponseDto> getAllOperationsOfAccount(@PathVariable String id){
        GetAccountOperationsQueryDto query = new GetAccountOperationsQueryDto(id);
        return accountService.handle(query);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> exceptionHandler(RuntimeException exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
