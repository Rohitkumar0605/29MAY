package com.banking.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.banking.dto.AccountDto;
import com.banking.dto.Message;
import com.banking.entity.Account;
import com.banking.entity.FundTransaction;
import com.banking.entity.User;
import com.banking.repository.AccountRepository;
import com.banking.repository.FundTransactionRepository;
import com.banking.repository.UserRepository;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class BankingServiceImplTest {

	@Mock
	AccountRepository accountRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	FundTransactionRepository fundTransactionRepo;

	@InjectMocks
	BankingServiceImpl bankingServiceImpl;

	static AccountDto accountDto = new AccountDto();
	static Account account = new Account();
	static User user = new User();

	static List<FundTransaction> ft = new ArrayList<>();
	static FundTransaction trans = new FundTransaction();

	static List<User> expectval1 = new ArrayList<User>();

	static Message msg = new Message();
	

	@BeforeClass
	public static void setUp() {
		accountDto.setAccountType("Saving");
		accountDto.setAddress("BLR");
		accountDto.setFirstName("Rohit");
		accountDto.setLastName("Kumar");
		accountDto.setPancard("cppcis099");

		account.setAccountId(null);
		account.setAccountType(accountDto.getAccountType());
		account.setAddress(accountDto.getAddress());
		account.setFirstName(accountDto.getFirstName());
		account.setLastName(accountDto.getLastName());
		account.setPancard(accountDto.getPancard());
		account.setStatus("Active");

		user.setUserId(null);
		user.setAccountType(account.getAccountType());
		user.setAmount(50000);
		user.setPassword("password");
		user.setUserName(accountDto.getFirstName().substring(0, 3) + accountDto.getLastName().substring(0, 3));
		user.setAccount(account);

		trans.setTransactionId(1L);
		trans.setUserId(2L);
		trans.setAccountId(3L);
		trans.setFromAccount("fromAccount");
		trans.setToAccount("toAccount");
		trans.setTransactionDate(new Date());
		trans.setAmount(500D);

		ft.add(trans);

		expectval1.add(user);
		
		msg.setStatus("Success");
		msg.setMessage("Success");
		msg.setUser(user);

	}

	@Test
	public void testCreate() {
		/*
		 * Mockito.when(userRepository.save(user)).thenReturn(user);
		 * Mockito.when(accountRepository.save(account)).thenReturn(account);
		 */
		User actval1 = bankingServiceImpl.create(accountDto);
		assertEquals(user.getUserName(), actval1.getUserName());

	}

	@Test
	public void getAllTransTest() {
		Mockito.when(fundTransactionRepo.findByUserId(2L)).thenReturn(ft);
		List<FundTransaction> actual = bankingServiceImpl.getTransactionDetails(2L);
		Assert.assertEquals(ft.size(), actual.size());

	}

	@Test
	public void testGetAllUser() {
		Mockito.when(userRepository.findAll()).thenReturn(expectval1);
		List<User> actval1 = bankingServiceImpl.getAll();
		Assert.assertEquals(expectval1.size(), actval1.size());
	}

	@Test
	public void testValidateLogin() {
		Mockito.when(userRepository.findByUserNameAndPassword("RohKum", "password")).thenReturn(user);
		Message actval3 = bankingServiceImpl.validateLogin("RohKum", "password");
		Assert.assertEquals(msg.getStatus(), actval3.getStatus());
	}

}
