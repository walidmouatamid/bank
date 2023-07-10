package com.societegenerale.bank.query_side.repositories;

import com.societegenerale.bank.query_side.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
