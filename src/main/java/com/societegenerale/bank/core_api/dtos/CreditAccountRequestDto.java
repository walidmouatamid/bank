package com.societegenerale.bank.core_api.dtos;

import java.math.BigDecimal;

public record CreditAccountRequestDto(String id, BigDecimal amount) { }
