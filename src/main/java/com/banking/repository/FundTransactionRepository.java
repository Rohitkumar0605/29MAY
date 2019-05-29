package com.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.entity.FundTransaction;

@Repository
public interface FundTransactionRepository extends JpaRepository<FundTransaction, Long> {

}
