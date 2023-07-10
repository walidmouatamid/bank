package com.societegenerale.bank.query_side.repositories;

import com.societegenerale.bank.query_side.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, String> {
}
