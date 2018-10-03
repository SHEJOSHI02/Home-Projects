package com.spring.demo.SpringJPATransaction.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.demo.SpringJPATransaction.exception.BankTransactionException;
import com.spring.demo.SpringJPATransaction.model.BankAccount;
import com.spring.demo.SpringJPATransaction.model.BankAccountInfo;
import com.spring.demo.SpringJPATransaction.repository.BankAccountDao;

@Service
@EnableTransactionManagement
public class BankAccountServiceImpl implements BankAccountService{
	
	@Autowired
	private BankAccountDao accountDao;
	@Autowired
    private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccountInfo> listBankAccountInfo() {
		
		String sql = "Select new " + BankAccountInfo.class.getName() //
                + "(e.id,e.fullName,e.balance) " //
                + " from " + BankAccount.class.getName() + " e ";
        Query query = entityManager.createQuery(sql, BankAccountInfo.class);
        List<BankAccountInfo> list = query.getResultList();
        return list;
	}

	 @Transactional(propagation = Propagation.MANDATORY )
	public void addAmount(Long id, double amount) throws BankTransactionException {
		 Optional<BankAccount> account = accountDao.findById(id);
		 BankAccount bankAccount = account.get();
	      
	        double newBalance = bankAccount.getBalance() + amount;
	        if (bankAccount.getBalance() + amount < 0) {
	            throw new BankTransactionException(
	                    "The money in the account '" + id + "' is not enough (" + bankAccount.getBalance() + ")");
	        }
	        bankAccount.setBalance(newBalance);
		
	}

	 @Transactional(propagation = Propagation.REQUIRES_NEW, 
             rollbackFor = BankTransactionException.class)
	public void sendMoney(Long fromAccountId, Long toAccountId, double amount) throws BankTransactionException {
	
		  addAmount(toAccountId, amount);
	        addAmount(fromAccountId, -amount);
	}

}
