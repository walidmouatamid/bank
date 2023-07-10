package com.societegenerale.bank.core_api.events;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class AccountCreditedEvent extends BaseEvent<String>{
    @Getter
    private final BigDecimal amount;

    public AccountCreditedEvent(String id, Date date, BigDecimal amount) {
        super(id, date);
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "AccountCreditedEvent{" +
                "amount=" + amount +
                '}'+ super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountCreditedEvent that = (AccountCreditedEvent) o;
        return Objects.equals(amount, that.amount) && super.equals(o);
    }

}
