package com.hatchways.finance.service;

import com.hatchways.finance.model.Account;
import com.hatchways.finance.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getByUserId(Integer userId) {
        return accountRepository.findByUserId(userId);
    }
}
