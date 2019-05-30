package com.banking.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.dto.AccountDto;
import com.banking.dto.Message;
import com.banking.entity.Account;
import com.banking.entity.FundTransaction;
import com.banking.entity.User;
import com.banking.repository.AccountRepository;
import com.banking.repository.FundTransactionRepository;
import com.banking.repository.UserRepository;

@Service
public class BankingServiceImpl implements BankingService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	FundTransactionRepository fundTransactionRepository;

	@Override
	@Transactional
	public User create(AccountDto accountDto) {
		Account account = new Account();
		account.setAccountType(accountDto.getAccountType());
		account.setAddress(accountDto.getAddress());
		account.setFirstName(accountDto.getFirstName());
		account.setLastName(accountDto.getLastName());
		account.setPancard(accountDto.getPancard());
		account.setStatus("Active");

		User user = new User();
		user.setAccountType(account.getAccountType());
		user.setAmount(50000);
		user.setPassword("password");
		user.setUserName(accountDto.getFirstName().substring(0, 3) + accountDto.getLastName().substring(0, 3));

		user.setAccount(account);

		accountRepository.save(account);
		userRepository.save(user);
		return user;
	}

	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

	@Override
	public Message validateLogin(String userName, String password) {

		Message msg = new Message();
		
		User user = new User();
		user = userRepository.findByUserNameAndPassword(userName, password);
		if (user != null) {
			msg.setStatus("Success");
			msg.setMessage("Success");
			msg.setUser(user);
			return msg;
		} else {
			msg.setStatus("Failure");
			msg.setMessage("Invalid Login");
			return msg;
		}
	}

	@Override
	public List<FundTransaction> getTransactionDetails(Long userId) {
		return fundTransactionRepository.findByUserId(userId);
	}

	@Override
	public Message fundTrasnferProcess(Long fromUserId, Long toUserId, double enteredAmount) {
		Message message = new Message();
		FundTransaction fundTransaction = new FundTransaction();
		

		User user = userRepository.findById(fromUserId).get();
		Long fromAccountId = user.getAccount().getAccountId();

		User user1 = userRepository.findById(toUserId).get();
		Long toAccountId = user.getAccount().getAccountId();

		Double fromAmount = user.getAmount();
		Double toAmount = user1.getAmount();

		Double famount = fromAmount - enteredAmount;
		Double tAmount = toAmount + enteredAmount;

		fundTransaction.setAccountId(fromAccountId);
		fundTransaction.setAmount(enteredAmount);
		fundTransaction.setFromAccount(user.getUserName());
		fundTransaction.setToAccount(user1.getUserName());
		fundTransaction.setTransactionDate(new Date());
		fundTransaction.setUserId(fromUserId);
		
		if (fromAmount > enteredAmount) {
			user.setAmount(famount);
			userRepository.save(user);
			
			user1.setAmount(tAmount);
			userRepository.save(user1);
			
//			userRepository.updateFundTransferNew(fromUserId, famount);
//			userRepository.updateFundTransferNew(toUserId, tAmount);
			fundTransactionRepository.save(fundTransaction);
			message.setStatus("success");
			message.setMessage("Transaction Completed");
			// return message;
		} else {
			message.setStatus("failure");
			message.setMessage("Insufficient fund.");
			// return message;
		}
		return message;
	}

}
