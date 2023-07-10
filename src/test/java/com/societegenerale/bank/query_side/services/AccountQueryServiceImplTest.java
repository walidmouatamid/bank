package com.societegenerale.bank.query_side.services;

import com.societegenerale.bank.core_api.dtos.AccountResponseDto;
import com.societegenerale.bank.core_api.dtos.GetAccountOperationsQueryDto;
import com.societegenerale.bank.core_api.dtos.GetAccountQueryDto;
import com.societegenerale.bank.core_api.dtos.GetAllAccountsQueryDto;
import com.societegenerale.bank.core_api.enums.AccountStatus;
import com.societegenerale.bank.core_api.enums.Currency;
import com.societegenerale.bank.core_api.events.AccountActivatedEvent;
import com.societegenerale.bank.core_api.events.AccountCreatedEvent;
import com.societegenerale.bank.core_api.events.AccountCreditedEvent;
import com.societegenerale.bank.core_api.events.AccountDebitedEvent;
import com.societegenerale.bank.query_side.entities.Account;
import com.societegenerale.bank.query_side.entities.Operation;
import com.societegenerale.bank.query_side.repositories.AccountRepository;
import com.societegenerale.bank.query_side.repositories.OperationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AccountQueryServiceImplTest {

    // Mock the dependencies
    OperationRepository operationRepository = Mockito.mock(OperationRepository.class);
    AccountRepository accountRepositoryMock = Mockito.mock(AccountRepository.class);

    // Create an instance of the handler under test
    AccountQueryServiceImpl accountQueryService = new AccountQueryServiceImpl(accountRepositoryMock, operationRepository);

    @Test
    void itShouldHandleAccountCreated() {
        String id = UUID.randomUUID().toString();
        AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent(id,
                new Date(),
                new BigDecimal(1000),
                Currency.EUR,
                AccountStatus.CREATED);

        accountQueryService.handle(accountCreatedEvent);
        Mockito.verify(accountRepositoryMock).save(Mockito.any(Account.class));

    }

    @Test
    void itShouldHandleAccountActivated() {
        String id = UUID.randomUUID().toString();
        Mockito.when(accountRepositoryMock.findById(id)).thenReturn(Optional.of(new Account(id,new BigDecimal(100),Currency.USD,AccountStatus.CREATED,null)));
        AccountActivatedEvent accountActivatedEvent = new AccountActivatedEvent(id,
                new Date(),
                AccountStatus.CREATED);
        accountQueryService.handle(accountActivatedEvent);
        Mockito.verify(accountRepositoryMock).save(Mockito.any(Account.class));
    }

    @Test
    void itShouldDebitedAccount() {
        String id = UUID.randomUUID().toString();
        Mockito.when(accountRepositoryMock.findById(id)).thenReturn(Optional.of(new Account(id,new BigDecimal(100),Currency.USD,AccountStatus.CREATED,new ArrayList<>())));
        AccountDebitedEvent accountDebitedEvent = new AccountDebitedEvent(id,new Date(), new BigDecimal(100));

        accountQueryService.handle(accountDebitedEvent);
        Mockito.verify(operationRepository).save(Mockito.any(Operation.class));
        Mockito.verify(accountRepositoryMock).save(Mockito.any(Account.class));
    }

    @Test
    void itShouldCreditedAccount() {
        String id = UUID.randomUUID().toString();
        Mockito.when(accountRepositoryMock.findById(id)).thenReturn(Optional.of(new Account(id,new BigDecimal(100),Currency.USD,AccountStatus.CREATED,new ArrayList<>())));
        AccountCreditedEvent accountCreditedEvent = new AccountCreditedEvent(id,new Date(), new BigDecimal(100));

        accountQueryService.handle(accountCreditedEvent);
        Mockito.verify(operationRepository).save(Mockito.any(Operation.class));
        Mockito.verify(accountRepositoryMock).save(Mockito.any(Account.class));
    }

    @Test
    void itShouldGetAccountById() {
        String id = UUID.randomUUID().toString();
        Mockito.when(accountRepositoryMock.findById(id)).thenReturn(Optional.of(new Account(id,new BigDecimal(100),Currency.USD,AccountStatus.CREATED,new ArrayList<>())));
        GetAccountQueryDto getAccountQueryDto = new GetAccountQueryDto(id);

        // Invoke the method
        accountQueryService.handle(getAccountQueryDto);

        // Verify the behavior
        Mockito.verify(accountRepositoryMock).findById(id);
    }

    @Test
    void itShouldGetAllAccounts() {
        String id = UUID.randomUUID().toString();
        List<Account> accountList = new ArrayList<>();
        Mockito.when(accountRepositoryMock.findAll()).thenReturn(new ArrayList<>(accountList));
        GetAllAccountsQueryDto getAllAccountsQueryDto = new GetAllAccountsQueryDto();
        GetAccountQueryDto getAccountQueryDto = new GetAccountQueryDto(id);

        // Invoke the method
        accountQueryService.handle(getAllAccountsQueryDto);

        // Verify the behavior
        Mockito.verify(accountRepositoryMock).findAll();
    }

    @Test
    void itShouldGetAllOperationsOfAccount() {
        String id = UUID.randomUUID().toString();
        List<Operation> operations = new ArrayList<>();
        Mockito.when(accountRepositoryMock.findById(id)).thenReturn(Optional.of(new Account(id,new BigDecimal(100),Currency.USD,AccountStatus.CREATED,new ArrayList<>())));

        GetAccountOperationsQueryDto getAccountOperationsQueryDto = new GetAccountOperationsQueryDto(id);


        // Invoke the method
        accountQueryService.handle(getAccountOperationsQueryDto);

        // Verify the behavior
        Mockito.verify(accountRepositoryMock).findById(id);
    }
}