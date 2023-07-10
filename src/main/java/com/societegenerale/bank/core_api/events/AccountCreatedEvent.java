package com.societegenerale.bank.core_api.events;

import com.societegenerale.bank.core_api.enums.AccountStatus;
import com.societegenerale.bank.core_api.enums.Currency;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

public class AccountCreatedEvent extends BaseEvent<String>{
    @Getter
    private BigDecimal balance;
    @Getter
    private Currency currency;
    @Getter
    private AccountStatus status;

    public AccountCreatedEvent(String id, Date date, BigDecimal balance, Currency currency, AccountStatus status) {
        super(id, date);
        this.balance = balance;
        this.currency = currency;
        this.status = status;
    }
}
