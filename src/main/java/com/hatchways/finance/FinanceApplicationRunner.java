package com.hatchways.finance;

import com.hatchways.finance.repository.AccountRepository;
import com.hatchways.finance.repository.TransactionRepository;
import com.hatchways.finance.repository.UserRepository;
import com.hatchways.finance.util.SeedDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FinanceApplicationRunner implements CommandLineRunner {

  @Autowired private UserRepository userRepository;
  @Autowired private AccountRepository accountRepository;
  @Autowired private TransactionRepository transactionRepository;

  @Override
  public void run(String... args) throws Exception {
    for (String arg : args) {
      if (arg.equals("seedData")) {
        SeedDataUtil util =
            new SeedDataUtil(userRepository, accountRepository, transactionRepository);
        util.deleteAll();
        util.seedFromFiles();
      }
    }
  }
}
