package com.societegenerale.bank.core_api.events;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class AccountDebitedEvent extends BaseEvent<String>{
    @Getter
    private final BigDecimal amount;

    public AccountDebitedEvent(String id, Date date, BigDecimal amount) {
        super(id, date);
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AccountDebitedEvent that = (AccountDebitedEvent) o;
        return Objects.equals(amount, that.amount) && super.equals(o);
    }
}
