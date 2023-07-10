package com.societegenerale.bank.commande_side.commands;

import lombok.Getter;

import java.math.BigDecimal;

public class DebitAccountCommand extends BaseCommand<String>{
    @Getter
    private BigDecimal amount;

    public DebitAccountCommand(String aggregateId, BigDecimal amount) {
        super(aggregateId);
        this.amount = amount;
    }
}
