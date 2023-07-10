package com.societegenerale.bank.core_api.dtos;

import com.societegenerale.bank.core_api.enums.Currency;
import java.math.BigDecimal;

public record CreateAccountRequestDto(BigDecimal balance, Currency currency) { }
