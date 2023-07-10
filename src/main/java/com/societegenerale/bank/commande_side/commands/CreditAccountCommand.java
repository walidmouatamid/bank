package com.societegenerale.bank.commande_side.commands;

import lombok.Getter;

import java.math.BigDecimal;

public class CreditAccountCommand extends BaseCommand<String>{
    @Getter
    private BigDecimal amount;

    public CreditAccountCommand(String aggregateId, BigDecimal amount) {
        super(aggregateId);
        this.amount = amount;
    }
}
