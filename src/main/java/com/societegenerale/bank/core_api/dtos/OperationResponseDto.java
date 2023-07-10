package com.societegenerale.bank.core_api.dtos;

import com.societegenerale.bank.core_api.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationResponseDto {
    private Date operationDate;
    private BigDecimal amount;
    private OperationType type;
}
