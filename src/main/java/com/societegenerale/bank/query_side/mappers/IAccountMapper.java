package com.societegenerale.bank.query_side.mappers;

import com.societegenerale.bank.core_api.dtos.AccountResponseDto;
import com.societegenerale.bank.query_side.entities.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IAccountMapper {
    IAccountMapper INSTANCE = Mappers.getMapper(IAccountMapper.class);
    AccountResponseDto accountToAccountResponseDto(Account account);
}
