package com.banking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.entity.FundTransaction;

@Repository
public interface FundTransactionRepository extends JpaRepository<FundTransaction, Long> {

	List<FundTransaction> findByUserId(Long userId);

}
