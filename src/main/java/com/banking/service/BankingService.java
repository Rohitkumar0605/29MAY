package com.banking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.banking.dto.AccountDto;
import com.banking.dto.Message;
import com.banking.entity.FundTransaction;
import com.banking.entity.User;

@Service
public interface BankingService {

	public User create(AccountDto accountDto);

	public List<User> getAll();

	public Message validateLogin(String userName, String password);

	public List<FundTransaction> getTransactionDetails(Long userId);
	
	public Message fundTrasnferProcess(Long fromUserId, Long toUserId, double enteredAmount);

}
