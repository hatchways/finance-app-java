package com.hatchways.finance.service;

import com.hatchways.finance.model.Transaction;
import com.hatchways.finance.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getForDateRange(LocalDateTime dateStart, LocalDateTime dateEnd) {
        return transactionRepository.findAllByDateBetween(dateStart, dateEnd);
    }
}
