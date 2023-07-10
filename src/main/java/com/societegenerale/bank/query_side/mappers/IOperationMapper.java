package com.societegenerale.bank.query_side.mappers;

import com.societegenerale.bank.core_api.dtos.OperationResponseDto;
import com.societegenerale.bank.query_side.entities.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IOperationMapper {
    IOperationMapper INSTANCE = Mappers.getMapper(IOperationMapper.class);
    OperationResponseDto operationToOperationResponseDto(Operation operation);
}
