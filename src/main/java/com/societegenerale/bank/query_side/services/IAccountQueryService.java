package com.societegenerale.bank.query_side.services;

import com.societegenerale.bank.core_api.dtos.*;
import com.societegenerale.bank.core_api.events.AccountActivatedEvent;
import com.societegenerale.bank.core_api.events.AccountCreatedEvent;
import com.societegenerale.bank.core_api.events.AccountCreditedEvent;
import com.societegenerale.bank.core_api.events.AccountDebitedEvent;

import java.util.List;

public interface IAccountQueryService {
    /**
     * Event Handlers
     */
    void handle(AccountCreatedEvent accountCreatedEvent);
    void handle(AccountActivatedEvent accountActivatedEvent);
    void handle(AccountDebitedEvent accountDebitedEvent);
    void handle(AccountCreditedEvent accountCreditedEvent);

    /**
     * Query Handlers
     */
    AccountResponseDto handle(GetAccountQueryDto getAccountQueryDto);
    List<AccountResponseDto> handle(GetAllAccountsQueryDto getAllAccountsQueryDto);
    List<OperationResponseDto> handle(GetAccountOperationsQueryDto getAccountOperationsQueryDto);


}
