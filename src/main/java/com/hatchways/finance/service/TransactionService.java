package com.hatchways.finance.service;

import com.hatchways.finance.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    List<Transaction> getForDateRange(LocalDateTime dateStart, LocalDateTime dateEnd);
}
