package com.hatchways.finance.service;

import com.hatchways.finance.model.Account;

import java.util.List;

public interface AccountService {
    /**
     * Get all accounts for a given user
     * @param userId the ID of the user
     * @return a List of accounts
     */
    List<Account> getByUserId(Integer userId);
}
