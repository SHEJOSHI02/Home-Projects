package com.spring.demo.SpringJPATransaction.service;

import java.util.List;

import com.spring.demo.SpringJPATransaction.exception.BankTransactionException;
import com.spring.demo.SpringJPATransaction.model.BankAccountInfo;

public interface BankAccountService {

	public List<BankAccountInfo> listBankAccountInfo();
	
	public void addAmount(Long id, double amount) throws BankTransactionException;
	
	public void sendMoney(Long fromAccountId, Long toAccountId, double amount) throws BankTransactionException;
	
}
