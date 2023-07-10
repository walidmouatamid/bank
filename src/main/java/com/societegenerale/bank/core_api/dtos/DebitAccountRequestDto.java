package com.societegenerale.bank.core_api.dtos;

import java.math.BigDecimal;

public record DebitAccountRequestDto(String id,BigDecimal amount) { }
