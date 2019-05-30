package com.banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.banking.dto.AccountDto;
import com.banking.dto.Message;
import com.banking.entity.FundTransaction;
import com.banking.entity.User;
import com.banking.service.BankingService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BankingController {

	@Autowired
	private BankingService bankingService;

	@PostMapping("/createAccount")
	public User createAccount(@RequestBody AccountDto accountDto) {
		return bankingService.create(accountDto);

	}

	@GetMapping("/listUser")
	public List<User> listUser() {
		return bankingService.getAll();
	}

	@GetMapping("/loginAccount/{userName}/{password}")
	public Message validateLogin(@PathVariable String userName, @PathVariable String password) {
		return bankingService.validateLogin(userName, password);
	}

	@GetMapping("/getTransactionDetails/{userId}")
	public List<FundTransaction> getTransactionDetails(@PathVariable Long userId) {
		return bankingService.getTransactionDetails(userId);
	}

	@PutMapping("/fundTransfer/{fromUserId}/{toUserId}/{enteredAmount}")
	public Message fundTransfer(@PathVariable Long fromUserId, @PathVariable Long toUserId,
			@PathVariable double enteredAmount) {
		return bankingService.fundTrasnferProcess(fromUserId, toUserId, enteredAmount);
	}

 public String  message ()
 {
	 return "abc";
 }

	public void helloWorld() {
		System.out.println("Lakshman Testing");
	}


}
