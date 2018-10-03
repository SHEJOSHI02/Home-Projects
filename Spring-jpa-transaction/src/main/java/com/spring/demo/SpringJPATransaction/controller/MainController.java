package com.spring.demo.SpringJPATransaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.demo.SpringJPATransaction.exception.BankTransactionException;
import com.spring.demo.SpringJPATransaction.model.BankAccountInfo;
import com.spring.demo.SpringJPATransaction.model.SendMoneyForm;
import com.spring.demo.SpringJPATransaction.service.BankAccountService;

@Controller
public class MainController {
	
	 @Autowired
	    private BankAccountService accountService;
	 
	    @RequestMapping(value = "/", method = RequestMethod.GET)
	    public @ResponseBody ModelAndView showBankAccounts(ModelAndView model) {
	        List<BankAccountInfo> list = accountService.listBankAccountInfo();
	 
	        model.addObject("accountInfos", list);
	        model.setViewName("accountsPage");
	        return model;
	    }
	 
	    @RequestMapping(value = "/sendMoney", method = RequestMethod.GET)
	    public String viewSendMoneyPage(Model model) {
	 
	        SendMoneyForm form = new SendMoneyForm(1L, 2L, 700d);
	 
	        model.addAttribute("sendMoneyForm", form);
	 
	        return "sendMoneyPage";
	    }
	 
	  
	    @RequestMapping(value = "/sendMoney", method = RequestMethod.POST)
	    public String processSendMoney(Model model, SendMoneyForm sendMoneyForm) {
	 
	        System.out.println("Send Money: " + sendMoneyForm.getAmount());
	 
	        try {
	        	accountService.sendMoney(sendMoneyForm.getFromAccountId(), //
	                    sendMoneyForm.getToAccountId(), //
	                    sendMoneyForm.getAmount());
	        } catch (BankTransactionException e) {
	            model.addAttribute("errorMessage", "Error: " + e.getMessage());
	            return "/sendMoneyPage";
	        }
	        return "redirect:/";
	    }
	 
	}

