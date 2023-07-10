package com.societegenerale.bank.query_side.services;

import com.societegenerale.bank.core_api.dtos.*;
import com.societegenerale.bank.core_api.enums.OperationType;
import com.societegenerale.bank.core_api.events.AccountActivatedEvent;
import com.societegenerale.bank.core_api.events.AccountCreatedEvent;
import com.societegenerale.bank.core_api.events.AccountCreditedEvent;
import com.societegenerale.bank.core_api.events.AccountDebitedEvent;
import com.societegenerale.bank.core_api.exceptions.AccountNotFoundException;
import com.societegenerale.bank.query_side.mappers.IAccountMapper;
import com.societegenerale.bank.query_side.mappers.IOperationMapper;
import com.societegenerale.bank.query_side.entities.Account;
import com.societegenerale.bank.query_side.entities.Operation;
import com.societegenerale.bank.query_side.repositories.AccountRepository;
import com.societegenerale.bank.query_side.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AccountQueryServiceImpl implements IAccountQueryService {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    private final IAccountMapper accountMapper = IAccountMapper.INSTANCE;
    private final IOperationMapper operationMapper = IOperationMapper.INSTANCE;


    @Override
    @EventHandler
    public void handle(AccountCreatedEvent accountCreatedEvent) {
        log.info("AccountCreatedEvent Handled with id=" +accountCreatedEvent.getId());
        Account account = new Account();
        account.setId(accountCreatedEvent.getId());
        account.setBalance(accountCreatedEvent.getBalance());
        account.setCurrency(accountCreatedEvent.getCurrency());
        account.setStatus(accountCreatedEvent.getStatus());
        accountRepository.save(account);
    }

    @Override
    @EventHandler
    public void handle(AccountActivatedEvent accountActivatedEvent) {
        log.info("AccountActivatedEvent Handled with id=" + accountActivatedEvent.getId());
        Account account=accountRepository.findById(accountActivatedEvent.getId()).get();
        account.setStatus(accountActivatedEvent.getStatus());
        accountRepository.save(account);
    }

    @Override
    @EventHandler
    public void handle(AccountDebitedEvent accountDebitedEvent) {
        log.info("AccountDebitedEvent Handled with id=" +accountDebitedEvent.getId());
        Account account=accountRepository.findById(accountDebitedEvent.getId()).get();
        account.setBalance(account.getBalance().subtract(accountDebitedEvent.getAmount()));

        Operation operation = new Operation();
        operation.setAccount(account);
        operation.setAmount(accountDebitedEvent.getAmount());
        operation.setType(OperationType.DEBIT);
        operation.setOperationDate(accountDebitedEvent.getDate());

        operationRepository.save(operation);
        account.getOperations().add(operation);

        accountRepository.save(account);
    }

    @Override
    @EventHandler
    public void handle(AccountCreditedEvent accountCreditedEvent) {
        log.info("AccountCreditedEvent Handled with id=" + accountCreditedEvent.getId());
        Account account=accountRepository.findById(accountCreditedEvent.getId()).get();
        account.setBalance(account.getBalance().add(accountCreditedEvent.getAmount()));

        Operation operation = new Operation();
        operation.setAccount(account);
        operation.setAmount(accountCreditedEvent.getAmount());
        operation.setType(OperationType.CREDIT);
        operation.setOperationDate(accountCreditedEvent.getDate());

        operationRepository.save(operation);
        account.getOperations().add(operation);

        accountRepository.save(account);
    }

    @Override
    @QueryHandler
    public AccountResponseDto handle(GetAccountQueryDto getAccountQueryDto) {
        log.info("GetAccountQueryDto Query Handled with id=" + getAccountQueryDto.id());
        Account account = accountRepository.findById(getAccountQueryDto.id()).orElseThrow(() ->new AccountNotFoundException("Account "+getAccountQueryDto.id()+" not found"));
        return accountMapper.accountToAccountResponseDto(account);
    }

    @Override
    @QueryHandler
    public List<AccountResponseDto> handle(GetAllAccountsQueryDto getAllAccountsQueryDto) {
        log.info("GetAllAccountsQueryDto Query Handled");
        List<Account> accounts = accountRepository.findAll();
        List<AccountResponseDto> accountsResponse = accounts
                .stream()
                .map(account -> accountMapper.accountToAccountResponseDto(account))
                .collect(Collectors.toList());
        return accountsResponse;
    }

    @Override
    @QueryHandler
    public List<OperationResponseDto> handle(GetAccountOperationsQueryDto getAccountOperationsQueryDto) {
        log.info("GetAccountOperationsQueryDto Query Handled with id=" + getAccountOperationsQueryDto.id());
        Collection<Operation> operations = accountRepository.findById(getAccountOperationsQueryDto.id()).orElseThrow(() ->new AccountNotFoundException("Account "+getAccountOperationsQueryDto.id()+" not found")).getOperations();
        List<OperationResponseDto> operationResponse = operations
                .stream()
                .map(operation -> operationMapper.operationToOperationResponseDto(operation))
                .collect(Collectors.toList());
        return operationResponse;
    }
}
