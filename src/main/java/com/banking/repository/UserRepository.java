package com.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.banking.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserNameAndPassword(String userName, String password);

	@Query(value = "update user u set amount = :amount where userId = :userId", nativeQuery = true)
	public void updateFundTransferNew(Long userId, Double amount);

}
