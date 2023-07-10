package com.societegenerale.bank.core_api.events;

import com.societegenerale.bank.core_api.enums.AccountStatus;
import lombok.Getter;

import java.util.Date;

public class AccountActivatedEvent extends BaseEvent<String> {
    @Getter
    private final AccountStatus status;

    public AccountActivatedEvent(String id, Date date, AccountStatus status) {
        super(id, date);
        this.status = status;
    }
}
