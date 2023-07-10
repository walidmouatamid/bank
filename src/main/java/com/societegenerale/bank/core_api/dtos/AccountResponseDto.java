package com.societegenerale.bank.core_api.dtos;

import com.societegenerale.bank.core_api.enums.AccountStatus;
import com.societegenerale.bank.core_api.enums.Currency;
import com.societegenerale.bank.query_side.entities.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collection;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDto {
    private BigDecimal balance;
    private Currency currency;
    private AccountStatus status;
    private Collection<Operation> operations;
}
