package com.spring.demo.SpringJPATransaction.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.demo.SpringJPATransaction.model.BankAccount;

@Repository
public interface BankAccountDao extends JpaRepository<BankAccount, Long>{

	Optional<BankAccount> findById(Long id);
	
}
