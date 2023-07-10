package com.societegenerale.bank.commande_side.services;

import com.societegenerale.bank.commande_side.commands.CreateAccountCommand;
import com.societegenerale.bank.commande_side.commands.CreditAccountCommand;
import com.societegenerale.bank.commande_side.commands.DebitAccountCommand;
import com.societegenerale.bank.core_api.dtos.CreateAccountRequestDto;
import com.societegenerale.bank.core_api.dtos.CreditAccountRequestDto;
import com.societegenerale.bank.core_api.dtos.DebitAccountRequestDto;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class AccountCommandServiceImpl implements IAccountCommandService{
    private final CommandGateway commandGateway;

    @Override
    public CompletableFuture<String> createAccount(CreateAccountRequestDto createAccountRequestDto) {
        return commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                createAccountRequestDto.balance(),
                createAccountRequestDto.currency()
        ));
    }

    @Override
    public CompletableFuture<String> debitAccount(DebitAccountRequestDto debitAccountRequestDto) {
        return commandGateway.send(new DebitAccountCommand(
                debitAccountRequestDto.id(),
                debitAccountRequestDto.amount()
        ));
    }

    @Override
    public CompletableFuture<String> creditAccount(CreditAccountRequestDto creditAccountRequestDto) {
        return commandGateway.send(new CreditAccountCommand(
                creditAccountRequestDto.id(),
                creditAccountRequestDto.amount()
        ));
    }
}
