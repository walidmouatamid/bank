package com.societegenerale.bank.commande_side.services;

import com.societegenerale.bank.core_api.dtos.CreateAccountRequestDto;
import com.societegenerale.bank.core_api.dtos.CreditAccountRequestDto;
import com.societegenerale.bank.core_api.dtos.DebitAccountRequestDto;
import java.util.concurrent.CompletableFuture;

public interface IAccountCommandService {
    CompletableFuture<String> createAccount(CreateAccountRequestDto createAccountRequestDto);
    CompletableFuture<String> debitAccount(DebitAccountRequestDto debitAccountRequestDto);
    CompletableFuture<String> creditAccount(CreditAccountRequestDto creditAccountRequestDto);

}
