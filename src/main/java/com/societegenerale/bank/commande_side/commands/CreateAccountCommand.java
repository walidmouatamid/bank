package com.societegenerale.bank.commande_side.commands;

import com.societegenerale.bank.core_api.enums.Currency;
import lombok.Getter;

import java.math.BigDecimal;
public class CreateAccountCommand extends BaseCommand<String>{
    @Getter
    private BigDecimal balance;
    @Getter
    private Currency currency;

    public CreateAccountCommand(String aggregateId, BigDecimal balance, Currency currency) {
        super(aggregateId);
        this.balance = balance;
        this.currency = currency;
    }
}
